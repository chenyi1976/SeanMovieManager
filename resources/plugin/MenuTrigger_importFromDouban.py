#get list from douban

doubanUserId = "chenyi1976"
#sleep time between each request, in seconds
requestGap = 1
#count per page to retrieve
countPerPage = 20
#max count to retrieve, -1 means "no limit"
retrieveLimitation = 25
#doubanListFile = "doubanlist.csv"
#"movie", "book", "music" , "tv" only
doubanCategory = ["movie"]
#douban status to pick up, based on different type, it has different status
#   book:   wish, reading, read
#   movie:  wish, watched
#   tv:     wish, watching, watched
#   music:  wish, listening, listened
doubanStatus = ["wish", "watched"]
delimeter = ','

#http://api.douban.com/people/chenyi1976/collection?cat=movie&status=wish
import urllib
import time
import xml.sax
from xml.sax.handler import *

foundResult = False

class DoubanHandler(ContentHandler):
   def __init__(self):
       ContentHandler.__init__(self)
       self.in_subject = 0
       self.in_title = 0
       self.in_author = 0
       self.in_aka = 0
       self.in_link = 0
       self.in_imdb = 0
       self.in_year = 0
       self.title = ''
       self.author = ''
       self.aka = ''
       self.link = ''
       self.imdb = ''
       self.year = ''
   def startDocument(self):
       print '--- Begin Document ---'
       self.in_subject = 0
       self.in_title = 0
       self.in_author = 0
       self.in_aka = 0
       self.in_link = 0
       self.in_imdb = 0
       self.in_year = 0
       self.title = ''
       self.author =''
       self.aka = ''
       self.link = ''
       self.imdb = ''
       self.year = ''
   def endDocument(self):
       print '--- End Document ---'
       self.in_subject = 0
       self.in_title = 0
       self.in_author = 0
       self.in_aka = 0
       self.in_link = 0
       self.in_imdb = 0
       self.in_year = 0
       self.title = ''
       self.author =''
       self.aka = ''
       self.link = ''
       self.imdb = ''
       self.year = ''
   def startElement(self, name, attrs):
       if name == 'db:subject':
#            print 'entering db:subject:'
           self.in_subject = 1
           print "Found"
           global foundResult
           print foundResult
           foundResult = True
       elif name == 'title' and self.in_subject:
           self.in_title = 1
       elif name == 'name' and self.in_subject:
           self.in_author = 1
       elif name == "link":
           if 'alternate' == attrs.getValue('rel'):
               self.in_link = 1
               self.link = attrs.getValue("href")
       elif name == 'db:attribute':
           if 'aka' == attrs.getValue('name'):
               self.in_aka = 1
           elif 'pubdate' == attrs.getValue('name'):
               self.in_year = 1
           elif 'imdb' == attrs.getValue('name'):
               self.in_imdb = 1
   def endElement(self, name):
       if name == 'db:subject':
           try:
#               line = self.title + delimeter + self.aka + delimeter + self.year + delimeter
#               line += self.author + delimeter + self.link + delimeter + self.imdb + '\n'
#               line = line.encode('utf-8')
#               fileHandler.writelines([line])
               addMovie(self.title)
           except Exception as inst:
               print inst
               pass
           self.title  = ''
           self.author = ''
           self.link = ''
           self.aka = ''
           self.year = ''
           self.imdb = ''
           self.in_subject = 0
       elif self.in_subject:
           if name == 'title':
               self.in_title = 0
           elif name == 'name':
               self.in_author = 0
           elif name == 'link':
               if self.in_link:
                   self.in_link = 0
           elif name == "db:attribute":
               if self.in_aka:
                   self.in_aka = 0
               elif self.in_year:
                   self.in_year = 0
               elif self.in_imdb:
                   self.in_imdb = 0
   def characters(self, ch):
       try:
           ch = ch.replace(',', '')
           if self.in_subject:
               if self.in_title:
                   self.title += ch
               elif self.in_author:
                   self.author += str(ch)
               elif self.in_aka:
                   self.aka += str(ch)
               elif self.in_year:
                   self.year += str(ch)
               elif self.in_imdb:
                   self.imdb += str(ch)
       except:
           pass

#fileHandler = open(doubanListFile, "a")
while 1:
#    try:
       print "beign retrieve"
       for status in doubanStatus:
           for cat in doubanCategory:
               index = 1
               while 1:
                   if retrieveLimitation > 0:
                       if index + countPerPage >= retrieveLimitation:
                           countPerPage = retrieveLimitation - index
                   print "status:" + status + ", cat:" + cat + ', start:' + str(index) + ", count:" + str(countPerPage)
                   url = urllib.urlopen("http://api.douban.com/people/" + doubanUserId + "/collection?cat=" + cat + "&status=" + status + "&start-index=" + str(index) + "&max-results=" + str(countPerPage))
                   index += countPerPage
                   
                   content = url.read()
               
                   xml.sax.parseString(content, DoubanHandler())
                   print "finish parseString"
                   print foundResult
                   if not foundResult:
                       print "no result, break"
                       break

                   if retrieveLimitation > 0:
                       if index >= retrieveLimitation:
                           print "limitation reached, break"
                           break

                   foundResult = False

                   print "scan finished, begin sleep " + str(requestGap) + " seconds."
                   time.sleep(requestGap)
#    except:
#        skipFileHandler.close()
       print "exception, exit..."
#       fileHandler.close()
       break
print "all finished"
#fileHandler.close()
