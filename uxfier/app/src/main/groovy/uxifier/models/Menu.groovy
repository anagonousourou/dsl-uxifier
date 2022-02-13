package uxifier.models

class Menu implements Component{
    String label
    String link
}


class NavigationMenu implements Component{
    boolean burger = false
    NavigationMenu(List<Component> componentList, boolean useBurger){
        this.componentList = componentList
        this.burger = useBurger
    }
}