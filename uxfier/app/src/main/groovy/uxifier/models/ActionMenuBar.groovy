package uxifier.models

class ActionMenuBar implements Component {
    ActionMenuBar(List<Component> componentList) {
        this.componentList = componentList
    }

    @Override
    def buildVue() {
        return null
    }
}

class Action implements Component {
    String label

    Action(String _label) {
        this.label = _label
    }

    @Override
    def buildVue() {
        return null
    }
}

class CartAction extends Action {
    boolean displayCartCount = true
    boolean displayCartIcon = false

    CartAction(String label, boolean displayCartCount, boolean  displayCartIcon) {
        super(label)
        this.displayCartCount = displayCartCount
        this.displayCartIcon = displayCartIcon
    }
}
