package uxifier

import uxifier.models.Menu


class NavigationMenuBuilder implements  GenericBuilder{

    private boolean burger = false

    def burger(boolean  useBurger){
        this.burger = useBurger
    }

    def Menu(@DelegatesTo(MenuBuilder) Closure closure){
        var builder = new MenuBuilder()
        var code = closure.rehydrate(builder,this,this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        this.componentList.add(builder.build())
    }

    boolean isBurger(){
        return burger
    }

}

class MenuBuilder{

    Menu menu = new Menu()


    def label(String _label){
        menu.setLabel(_label)
    }

    def link(String _link){
        menu.setLink(_link)
    }

    Menu build(){
        return this.menu
    }
}
