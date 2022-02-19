package uxifier.models

class Menu implements Component {
    String label
    String link
    String icon

    @Override
    def buildVue() {
        return null
    }
}


class NavigationMenu implements Component {
    NavigationMenuType menuType
    String applicationName

    NavigationMenu(List<Component> componentList, NavigationMenuType menuType, String applicationName) {
        this.componentList = componentList
        this.menuType = menuType
        this.applicationName = applicationName
    }

    @Override
    def buildVue() {
        return null
    }
}

enum NavigationMenuType {
    Drawer,
    Navbar
}