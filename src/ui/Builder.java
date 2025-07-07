package ui;

public class Builder {
    private Menu rootMenu;
    private static Builder instance;

    private Builder() {
    }

    public static Builder getInstance() {
        if (instance == null) {
            instance = new Builder();
        }
        return instance;
    }

    public void buildMenu() {

    }

    public Menu getRootMenu() {
        if (rootMenu == null) {
            buildMenu();
        }
        return rootMenu;
    }
}
