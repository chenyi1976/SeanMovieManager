import os, shutil, time

appFolder = os.getenv("HOME") + '/Library/Application Support/MovieManager/'
shutil.copytree(appFolder + "DB", appFolder + time.strftime('%Y%m%d%H%M%S'))