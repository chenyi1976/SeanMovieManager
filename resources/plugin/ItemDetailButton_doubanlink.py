import json
import urllib2

ATTRIBUTE_IMDB='imdb_id'
JSON_ATTR_ALT='alt'
JSON_ATTR_ID='id'
JSON_ID_PREFIX='http:\/\/api.douban.com\/movie\/'

currentMovieId = getCurrentMovieId()
tmdb_ID = getAttributeValue(currentMovieId, ATTRIBUTE_IMDB);
douban_URL = "https://api.douban.com/v2/movie/imdb/" + tmdb_ID
json_string=urllib2.urlopen(douban_URL).read()
data = json.loads(json_string)
douban_url=data.get(JSON_ATTR_ALT)
douban_id=data.get(JSON_ATTR_ID)
print douban_id
if douban_id.startswith(JSON_ID_PREFIX):
    douban_id=douban_id(len(JSON_ID_PREFIX))
print douban_id
#os.system(douban_url)
