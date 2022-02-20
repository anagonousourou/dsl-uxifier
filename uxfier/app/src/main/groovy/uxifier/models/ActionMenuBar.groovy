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
    boolean displayCartIcon = false
    CartPreview cartPreview = null

    CartAction(String label, boolean displayCartCount, boolean  displayCartIcon, CartPreview cartPreview) {
        super(label)
        this.displayCartCount = displayCartCount
        this.displayCartIcon = displayCartIcon
        this.cartPreview = cartPreview
    }
}
