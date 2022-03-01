package uxifier.models

import uxifier.vue.project.models.VueCartActionMenu
import uxifier.vue.project.models.VueCartPreview
import uxifier.vue.project.models.VueCartPreviewArticle

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
}

class CartAction extends Action {
    boolean displayCartCount = true
    boolean displayCartIcon = false
    CartPreview cartPreview = null
    String previewAction

    CartAction(String label, boolean displayCartCount, boolean  displayCartIcon, CartPreview cartPreview, String previewAction) {
        super(label)
        this.displayCartCount = displayCartCount
        this.displayCartIcon = displayCartIcon
        this.cartPreview = cartPreview
        this.previewAction = previewAction
    }
    /**
     * Will use this for onclick event
     * @return
     */

}
