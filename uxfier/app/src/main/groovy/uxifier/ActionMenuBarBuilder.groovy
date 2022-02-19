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
    private boolean useIcon

    private String previewType = ''

    def label(String _label) {
        this._label = _label
    }

    def propertyMissing(String propertyName) {
       if(propertyName.equals("displayItemCount")){
           this._dc = true
       }
        else if(propertyName.equals("useIcon")){
           this.useIcon = true
       }
    }

    def onhover(stuff){
        println("In onhover")
        [CartPreview: {cl -> println(cl)}]
    }

    def CartPreview (@DelegatesTo(value= CartPreviewBuilder, strategy = Closure.DELEGATE_FIRST) Closure closure){
        var builder = new CartPreviewBuilder()
        var code = closure.rehydrate(builder, this,this)
        code()

    }

    def build() {
        return new CartAction(this._label, this._dc, this.useIcon)
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
