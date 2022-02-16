package uxifier

import uxifier.models.Action
import uxifier.models.ActionMenuBar
import uxifier.models.CartAction

class ActionMenuBarBuilder {

    List<Action> actionList = new ArrayList<>()

    def Action(@DelegatesTo(ActionBuilder) Closure closure) {
        var builder = new ActionBuilder()
        var code = closure.rehydrate(builder, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
        this.actionList.add(builder.build())
    }



    def build() {
        return new ActionMenuBar(actionList)
    }
}

class CartActionBuilder {
    private String _label
    private boolean _dc

    def label(String _label) {
        this._label = _label
    }

    def displayItemCount(boolean dc) {
        this._dc = dc
    }

    def build() {
        return new CartAction(this._label, this._dc)
    }
}

class ActionBuilder {
    private String _label

    def label(String _label) {
        this._label = _label
    }

    def build() {
        return new Action(this._label)
    }
}
