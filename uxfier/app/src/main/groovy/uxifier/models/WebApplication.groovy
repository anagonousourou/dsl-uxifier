package uxifier.models

class WebApplication {

    String name

    List<WebPage> pages = new ArrayList<>()

    @Override
    String toString() {
        return "WebApplication { name = ${name}, pages = ${pages} }"
    }


    def addWebPage(WebPage page){
        this.pages.add(page)
    }
}

class WebPage implements Component{
    String name
    String title

    @Override
    String toString() {
        return "WebPage {title = ${title} , name = ${name}, components = ${componentList} }"
    }
}

class Header implements Component{
    Header(List<Component> componentList){
        this.componentList = componentList
    }
    @Override
    String toString(){
        return "Header {components = ${componentList} }"
    }
}

class HorizontalLayout implements Component{
        HorizontalLayout(List<Component> componentList){
            this.componentList = componentList
        }
}

class VerticalLayout implements Component{

}

class SocialMediaGroup implements Component{
    SocialMediaGroup(List<Component> componentList){
        this.componentList = componentList
    }
    String toString(){
        return "SocialMediaGroup {components = ${componentList} }"
    }
}

class SocialMedia implements Component{
    SocialMediaType type
    String url

    @Override
    String toString() {
        return "SocialMedia {type = ${type},url = ${url} }"
    }
}


enum SocialMediaType{
    FACEBOOK,
    LINKEDIN,
    INSTAGRAM,
    TWITTER,
    PINTEREST
}


trait Component implements ApplicationModelVisitable{
    List<Component> componentList

    def addComponent(Component component){
        this.componentList.add(component)
    }

    String toString(){
        return "Component {components = ${componentList} }"
    }

    @Override
    def accept(ApplicationModelVisitor visitor) {
        visitor.visit(this)
    }
}

interface ApplicationModelVisitable{
    def accept(ApplicationModelVisitor visitor)
}

interface ApplicationModelVisitor{
    def visit(SocialMedia media)
    def visit(HorizontalLayout layout)
    def visit(Component component)
    def visit(SocialMediaGroup socialMediaGroup)
    def visit(Header header)
    def visit(WebApplication application)
    def visit(WebPage webPage)
}

trait LeafComponent{

}

trait CompositeComponent{

}


