from javax.swing import JOptionPane
option = JOptionPane.showConfirmDialog(getMainFrame(), 'Auto import sample data?', 'Auto import', JOptionPane.YES_NO_OPTION)
if option == JOptionPane.YES_OPTION:
    addMovie('300');
    addMovie('Prometheus');
    addMovie('Alien');
    addMovie('Superman');
    addMovie('Spiderman');
    addMovie('Defiance');
    addMovie('Taken');
    addMovie('Chronicle');
    addMovie('Brave');
    addMovie('Hellboy');
