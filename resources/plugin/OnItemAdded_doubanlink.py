######################################################################
# get the douban.com id by imdb.com id
# NOT working, due to jython does not support ssl currently.
######################################################################

import urllib2

ATTRIBUTE_IMDB='imdb_id'
ATTRIBUTE_DOUBAN='douban_id'
JSON_ATTR_ALT='alt'
JSON_ATTR_ID='id'
JSON_ID_PREFIX='http://api.douban.com/movie/'

currentMovieId = getCurrentMovieId()
print 'currentMovieId:' + str(currentMovieId)
tmdb_ID = getAttributeValue(currentMovieId, ATTRIBUTE_IMDB);
print 'tmdb_ID:' + str(tmdb_ID)
douban_URL = "https://api.douban.com/v2/movie/imdb/" + tmdb_ID
print 'douban_URL:' + str(douban_URL)
json_string=urllib2.urlopen(douban_URL).read()
data = json.loads(json_string)
print 'data' + str(data)
douban_url=data.get(JSON_ATTR_ALT)
douban_id=data.get(JSON_ATTR_ID)
print douban_id
if douban_id.startswith(JSON_ID_PREFIX):
    douban_id=douban_id[len(JSON_ID_PREFIX):]
print douban_id
setAttributeValue(currentMovieId, ATTRIBUTE_DOUBAN, douban_id)
