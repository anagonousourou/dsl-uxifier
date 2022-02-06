package uxifier.vue.project.models

class VueComponent {
    VueTemplateElement template;
    ScriptElement script;
    String name


    def toCode(){
    }
}

class VueTemplateElement{
    List<HtmlElement> elements = new ArrayList<>()
}

class ScriptElement{

}

trait HtmlElement {

}
trait LeafHtmlElement implements HtmlElement{

}
trait CompositeHtmlElement implements HtmlElement{
    List<HtmlElement> elements = new ArrayList<>()
}

class HorizontalLayout implements HtmlElement{

}

class VerticalLayout implements HtmlElement{

}

