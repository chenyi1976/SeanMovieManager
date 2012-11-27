#this script is modified from http://tomazeli.wordpress.com/2011/03/08/download-trailers-from-apple-script/

#!/usr/bin/python

import urllib
import re
import sys
import os
import string

from urllib import FancyURLopener

class myOpenUrl(FancyURLopener):
    version = 'Mozilla/5.0 (Windows; U; Windows NT 5.1; it; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11'

#strap movie url from html
def getMovieUrl(movieName, resolution):

    moviePageUrl = getMoviePage(movieName,resolution)
    print moviePageUrl
    f = urllib.urlopen(moviePageUrl)
    s = f.read()

    s = re.findall(r'http.+apple.+'+resolution+'.+mov', s)

    if s:
        return s[0]
    else:
        print "Trailer not found"


#strap movie page from google search
def getMoviePage(movieName, resolution):
    try:

        myopener = myOpenUrl()
        page = myopener.open('http://www.google.com/search?q='+ string.join(movieName.split(), "+") + "+site:http://www.hd-trailers.net/")
        html = page.read()

        s = re.findall(r'href=[\'"]/url.q=([^\'"& >]+)',html)
        return s[0]

    except e:
        print "Search failed: %s" % e

def main():

    #if you want you can change to 720 or 1080, but is not garantee that it will find it
    resolution = "480"

    movieNameList = []
    if len(sys.argv) > 1:
        if (sys.argv[1] == "-h"):
            print "usages: ./trailerDownloader.py"
            print "OR"
            print "usage: ./trailerDownloader.py listOfMovie.txt"
            sys.exit(0)
        source = open(sys.argv[1], 'r')
        movieNameList = source.readlines()
        source.close()
    else:
        movieName = raw_input("Name of the movie : ")
        movieNameList.append(movieName)

    from javax.swing import JOptionPane
    movieName = JOptionPane.showInputDialog(getMainFrame(), 'Please input movie name you want to add.', 'Add movie', JOptionPane.QUESTION_MESSAGE)
    if movieName == None:
        return

    print "Searching for '"+movieName+"'..."
    movieUrl = getMovieUrl(movieName,resolution)

    if movieUrl:
        try:
            print "starting to download : "+ movieUrl
            cmd = 'wget -U QuickTime/7.6.2 ' + movieUrl
            os.system(cmd)
        except e:
            print "Error when trying to download : " + movieName
    else:
        print "movie not found"

main()
