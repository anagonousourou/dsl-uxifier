package uxifier.models

class Menu implements Component {
    String label
    String link
    String icon
}


class NavigationMenu implements Component {
    NavigationMenuType menuType

    NavigationMenu(List<Component> componentList, NavigationMenuType menuType) {
        this.componentList = componentList
        this.menuType = menuType
    }
}

enum NavigationMenuType {
    Drawer,
    Navbar
}