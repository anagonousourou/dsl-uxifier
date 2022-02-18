package uxifier.models

class Menu implements Component {
    String label
    String link
    String icon
}


class NavigationMenu implements Component {
    NavigationMenuType menuType
    String applicationName

    NavigationMenu(List<Component> componentList, NavigationMenuType menuType, String applicationName) {
        this.componentList = componentList
        this.menuType = menuType
        this.applicationName = applicationName
    }
}

enum NavigationMenuType {
    Drawer,
    Navbar
}