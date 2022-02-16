package uxifier.models

class ActionMenuBar implements Component {
    ActionMenuBar(List<Component> componentList) {
        this.componentList = componentList
    }
}

class Action implements Component {
    String label

    Action(String _label) {
        this.label = _label
    }
}

class CartAction extends Action {
    boolean displayCartCount = true

    CartAction(String label, boolean displayCartCount) {
        super(label)
        this.displayCartCount = displayCartCount
    }
}
