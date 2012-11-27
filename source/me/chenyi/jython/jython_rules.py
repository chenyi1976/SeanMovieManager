#jython 2.5 does not support json, we use third party json implementation from http://jyson.xhaus.com/
import com.xhaus.jyson.JysonCodec as json

import sys

#sys.add_package('me.chenyi.jython')

from me.chenyi.jython import ScriptLibrary

scriptLibrary = ScriptLibrary.getInstance()

def getCurrentMovieId():
    return scriptLibrary.getCurrentMovieId()

def setCurrentMovieId(id):
    return scriptLibrary.setCurrentMovieId(id)

def addMovie(movieName):
    return scriptLibrary.addMovie(movieName)

def addMovies(movieName):
    return scriptLibrary.addMovies(movieName)

def addMovies(movieName, maxCount):
    return scriptLibrary.addMovies(movieName, maxCount)

def updateMovie(movieId):
    return scriptLibrary.updateMovie(movieName)

def getMovieCount():
    return scriptLibrary.getMovieCount()

def getAttributeValue(itemId, attributeName):
    return scriptLibrary.getAttributeValue(itemId, attributeName)

def setAttributeValue(itemId, attributeName, attributeValue):
    return scriptLibrary.setAttributeValue(itemId, attributeName, attributeValue)

def getAttributeName(attributeId):
    return scriptLibrary.getAttributeName(attributeId)

def openUrlInBrowser(url):
    return scriptLibrary.openUrlInBrowser(url)

def getMainFrame():
    return scriptLibrary.getMainFrame()

