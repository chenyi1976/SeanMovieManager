from javax.swing import JFileChooser, JOptionPane,
chooser = JFileChooser()
option = chooser.showOpenDialog(getMainFrame())
if option == JFileChooser.APPROVE_OPTION:
    file = chooser.getSelectedFile()
    if not file.isDirectory():
        file = file.getParentFile()
    folderName = file.getName()
    #need to sort out get movie name by its folder name
    #movieName = folderName.replace('.', '')
    if movieName != None:
        movieNames = searchMovieByName(movieName)
        if movieNames == 0:
            return
        movieName = movieNames[0]
        if movieNames.length() > 1:
            comboBox = JComboBox(movieNames)
            option = JOptionPane.showOptionDialog(frame, comboBox, "Title", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if option == JOptionPane.OK_OPTION:
                movieName = comboBox.getSelectedItem()
        addMovie(movieName);

