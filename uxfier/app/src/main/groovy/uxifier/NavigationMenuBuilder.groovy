package uxifier

import uxifier.models.Menu
import uxifier.models.NavigationMenuType


class NavigationMenuBuilder implements GenericBuilder {

    private boolean burger = false
    private NavigationMenuType menuType = NavigationMenuType.Navbar

    def burger(boolean useBurger) {
        this.burger = useBurger
    }

    def type(String _type) {
        this.menuType = NavigationMenuType.valueOf(_type)
    }

    def Menu(@DelegatesTo(MenuBuilder) Closure closure) {
        var builder = new MenuBuilder()
        var code = closure.rehydrate(builder, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        this.componentList.add(builder.build())
    }

    def CartAction(@DelegatesTo(CartActionBuilder) Closure closure) {
        var builder = new CartActionBuilder()
        var code = closure.rehydrate(builder, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
        this.componentList.add(builder.build())
    }

    NavigationMenuType getMenuType() {
        return this.menuType
    }

    boolean isBurger() {
        return burger
    }

}

class MenuBuilder {

    Menu menu = new Menu()


    def label(String _label) {
        menu.setLabel(_label)
    }

    def link(String _link) {
        menu.setLink(_link)
    }

    def icon(String _icon) {
        menu.setIcon(_icon)
    }

    Menu build() {
        return this.menu
    }
}
