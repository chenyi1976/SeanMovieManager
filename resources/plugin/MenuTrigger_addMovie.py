from javax.swing import JOptionPane
movieName = JOptionPane.showInputDialog(getMainFrame(), 'Please input movie name you want to add.', 'Add movie', JOptionPane.QUESTION_MESSAGE)
if movieName != None:
    addMovie(movieName);
