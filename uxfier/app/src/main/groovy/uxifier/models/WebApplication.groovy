package uxifier.models

import uxifier.vue.project.models.*


class WebApplication {

    String name
    String title
    List<WebPage> pages = new ArrayList<>()

    NavigationMenu navigationMenu

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

    @Override
    def buildVue() {
        return null
    }
}

class Catalog implements Component{
    Product product
    Filter filter

    Catalog(List<Component> componentList){
        for(Component c : componentList){
            if (c instanceof Product){
                product = c
            }
            if(c instanceof  Filter){
                filter = c
            }
        }
    }

    @Override
    String toString(){
        return "product : " + product + "\nfilter : " + filter
    }

    @Override
    def buildVue() {
        return null
    }
}


class Product implements Component {

    Rating rating

    @Override
    String toString(){

        return "rating : "+ rating+"\n"
    }

    @Override
    def buildVue() {
        return null
    }
}

class Rating implements Component{
    RatingType ratingType

    @Override
    String toString(){
        return "rating type : " + ratingType
    }

    @Override
    def buildVue() {
        return null
    }
}

enum RatingType {
    Stars,
    Bar,
    Mark

}


class Filter implements Component{

    PriceFilter priceFilter

    GenericFilters genericFilters

    Filter(List<Component> compoents){

        for(Component c: compoents){
            if(c instanceof PriceFilter){
                this.priceFilter = (PriceFilter) c
            }

            if(c instanceof GenericFilters){
                this.genericFilters = (GenericFilters) c
            }

        }

    }

    @Override
    def buildVue() {
        return null
    }
}

class GenericFilter implements Component{

    String targetAtributName
    String targetAtributType

    @Override
    String toString(){
        return "targetAtributName : " + targetAtributName + "\ntargetAtributType" + targetAtributType
    }

    @Override
    def buildVue() {
        return null
    }
}

class PriceFilter implements Component{

    PriceType priceType

    @Override
    String toString(){
        return "type : " + priceType
    }

    @Override
    def buildVue() {
        return null
    }
}

enum PriceType {

    Range,
    Bar,
}




class Header implements Component{
    Header(List<Component> componentList){
        this.componentList = componentList
    }
    @Override
    String toString(){
        return "Header {components = ${componentList} }"
    }

    @Override
    def buildVue() {
        return null
    }
}

class HorizontalLayout implements Component{
        HorizontalLayout(List<Component> componentList){
            this.componentList = componentList
        }

    @Override
    def buildVue() {
        return null
    }
}

class VerticalLayout implements Component{

    @Override
    def buildVue() {
        return null
    }
}


class GenericFilters implements Component {
    GenericFilters(List<Component> componentList){
        this.componentList = componentList
    }

    @Override
    String toString() {
        return "GenericFilters {components = ${componentList} }"
    }

    @Override
    def buildVue() {
        return null
    }
}



class SocialMediaGroup implements Component{
    SocialMediaGroup(List<Component> componentList){
        this.componentList = componentList
    }
    String toString(){
        return "SocialMediaGroup {components = ${componentList} }"
    }

    @Override
    def buildVue() {
        return null
    }
}

class SocialMedia implements Component{
    SocialMediaType type
    String url

    @Override
    String toString() {
        return "SocialMedia {type = ${type},url = ${url} }"
    }

    @Override
    def buildVue() {
        return null
    }
}

enum SocialMediaType {
    Facebook,
    LinkedIn,
    Instagram,
    Twitter,
    Pinterest
}

class Form implements Component{
    String name

    Form(){}

    Form(List<Component> componentList){
        this.componentList = componentList
    }

    Form(String name, List<Component> componentList){
        this.componentList = componentList
        this.name = name
    }

    @Override
    public String toString() {
        return "Form{" +
                "name='" + name + '\'' +
                ", components = ${componentList} " +
                '}';
    }

    @Override
    def buildVue() {
        var vue = new VueJsForm()
        vue.name = this.name
        for(Component c : this.componentList){
            if(c instanceof FieldGroup){
                for(Field f : (c.componentList as List<Field>)){
                    var tmpField = new VueJsField()
                    tmpField.setName(f.name)
                    tmpField.setType(f.type)
                    vue.fields.add(tmpField)
                }
            }
        }
        return vue
    }
}

class FieldGroup implements Component{
    FieldGroup(List<Component> componentList){
        this.componentList = componentList
    }
    @Override
    String toString(){
        return "FieldGroup {components = ${componentList} }"
    }

    @Override
    def buildVue() {
        return null
    }
}

class Field implements Component{
    String type
    String name


    @Override
    public String toString() {
        return "Field{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    def buildVue() {
        return null
    }
}

class AccordionGroup implements Component{

    AccordionGroup(){
        this.componentList = new ArrayList<>()
    }

    AccordionGroup(List<Component> componentList){
        this.componentList = componentList
    }
    @Override
    String toString(){
        return "AccordionGroup {components = ${componentList} }"
    }

    @Override
    def buildVue() {
        var tmp = new VueJsAccordionGroup();
        for(Accordion a : (this.componentList as List<Accordion>)){
            VueJsAccordion tmpAcc = new VueJsAccordion()
            tmpAcc.name = a.name
            for(Component c : a.componentList){
                var vue = c.buildVue()
                if(vue == null)
                    continue
                tmpAcc.components.add(vue as VueGeneratable)
            }
            tmp.accordions.add(tmpAcc)
        }
        return tmp
    }
}

class Accordion implements Component{
    String name

    Accordion(){
        this.componentList = new ArrayList<>()
    }


    @Override
    String toString(){
        return "Accordion {name = ${name}, components = ${componentList}}"
    }

    @Override
    def buildVue() {
        return null
    }
}


trait Component implements ApplicationModelVisitable {
    List<Component> componentList

    def addComponent(Component component) {
        this.componentList.add(component)
    }

    String toString() {
        return "Component {components = ${componentList} }"
    }

    abstract def buildVue()

    @Override
    def accept(ApplicationModelVisitor visitor) {
        visitor.visit(this)
    }
}

interface ApplicationModelVisitable {
    def accept(ApplicationModelVisitor visitor)
}

interface ApplicationModelVisitor{
    def visit(SocialMedia media)

    def visit(HorizontalLayout layout)

    def visit(Component component)
    def visit(SocialMediaGroup socialMediaGroup)

    def visit(Header header)

    def visit(WebApplication application)

    def visit(Catalog application)
    def visit(WebPage webPage)

    def visit(NavigationMenu navigationMenu)

    def visit(Menu menu)
    def visit(Form form)
    def visit(Field field)
    def visit(AccordionGroup accordionGroup)

    def visit(Action action)

    def visit(ActionMenuBar menuBar)

    def visit(CartAction action)
    def visit(Accordion accordion)
    def visit(Filter filter)
    def visit(PriceFilter priceFilter)
    def visit(Product product)
    def visit(GenericFilters genericFilters)
    def visit(GenericFilter genericFilter)
}

trait LeafComponent{

}

trait CompositeComponent{

}

