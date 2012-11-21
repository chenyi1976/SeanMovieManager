#import webbrowser

ATTRIBUTE_TITLE='title'

currentMovieId = getCurrentMovieId()
print 'currentMovieId:' + str(currentMovieId)
title = getAttributeValue(currentMovieId, ATTRIBUTE_TITLE);
thepiratebay_url='https://thepiratebay.se/search/' + title + '%20720p/0/99/0'
#webbrowser.open(thepiratebay_url)
openUrlInBrowser(thepiratebay_url)
