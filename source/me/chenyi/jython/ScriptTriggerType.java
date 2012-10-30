package me.chenyi.jython;

// External imports
// None

// Local imports
// None

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public enum ScriptTriggerType
{
    OnAppStart("OnAppStart"),
    OnAppExit("OnAppExit"),
    OnAppFocusGained("OnAppFocusGained"),
    OnAppFocusLost("OnAppFocusLost"),
    OnItemAdded("OnItemAdded"),
    OnItemUpdated("OnItemUpdated"),
    OnItemDeleted("OnItemDeleted"),
    OnItemSelected("OnItemSelected"),
    OnItemPlayed("OnItemPlayed"),
    ToolbarTrigger("ToolbarTrigger"),
    MenuTrigger("MenuTrigger"),
    ItemDetailButton("ItemDetailButton");

    private String triggerTypeName;

    private ScriptTriggerType(String triggerTypeName)
    {
        this.triggerTypeName = triggerTypeName;
    }

    public String getTriggerTypeName()
    {
        return triggerTypeName;
    }

    @Override
    public String toString()
    {
        return triggerTypeName;
    }
}
