/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package uxifier

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import uxifier.models.Catalog
import uxifier.models.Component
import uxifier.models.Filter
import uxifier.models.GenericFilter
import uxifier.models.Header
import uxifier.models.HorizontalLayout
import uxifier.models.PriceFilter
import uxifier.models.PriceType
import uxifier.models.Product
import uxifier.models.Rating
import uxifier.models.RatingType
import uxifier.models.SocialMedia
import uxifier.models.SocialMediaGroup
import uxifier.models.SocialMediaType
import uxifier.models.WebApplication
import uxifier.models.WebPage
import uxifier.models.visitors.ApplicationModelVisitorVueJS

class App {

    static void main(String[] args) {
        ScriptInterpreter dsl = new ScriptInterpreter()
        if (args.length > 0) {
            dsl.eval(new File(args[0]))
        } else {
            System.out.println("/!\\ Missing arg: Please specify the path to a Groovy script file to execute")
        }
    }
}
class ScriptInterpreter{
    private GroovyShell shell
    private CompilerConfiguration configuration
    private Binding binding
    private UXifier basescript

    ScriptInterpreter() {
        binding = new Binding()
        configuration = getDSLConfiguration()
        configuration.setScriptBaseClass("uxifier.UXifier")
        shell = new GroovyShell(configuration)
    }


    void eval(File scriptFile) {
        Script script = shell.parse(scriptFile)

        script.setBinding(binding)

        script.run()
    }

    private static CompilerConfiguration getDSLConfiguration() {
        def secure = new SecureASTCustomizer()
        secure.with {

            closuresAllowed = true

            methodDefinitionAllowed = true

            importsWhitelist = [
                    'java.lang.*'
            ]
            staticImportsWhitelist = []
            staticStarImportsWhitelist = ['uxifier.models.SocialMedia.*']



            constantTypesClassesWhiteList = [
                    int, Integer, Number, Integer.TYPE, String, Object
            ]

            receiversClassesWhiteList = [
                    int, Number, Integer, String, Object
            ]
        }

        def configuration = new CompilerConfiguration()
        configuration.addCompilationCustomizers(secure)

        return configuration
    }
}
class WebApplicationBuilder{

    WebApplication webApplication

    WebApplicationBuilder(WebApplication webApplication) {
        this.webApplication = webApplication
    }

    def name(String appName){
        if(appName.contains(' ')){
            println "Invalid name for application '${appName}' : it should not have spaces"
            System.exit(1)
        }
        this.webApplication.name = appName
    }

    def build(){
        return this.webApplication
    }

    def WebPage(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=WebPageBuilder) Closure closure){
        var webPageBuilder = new WebPageBuilder(webApplication)
        def code = closure.rehydrate(webPageBuilder, this, this)//permet de définir que tous les appels de méthodes
        code.resolveStrategy = Closure.DELEGATE_ONLY//à l'intérieur de la closure seront résolus en utilisant le delegate
        code()
        this.webApplication.addWebPage(webPageBuilder.buildPage())
    }
}
class WebPageBuilder implements GenericBuilder{
    String _title
    String _name
    WebApplication webApplication

    WebPageBuilder(WebApplication webApplication){
        this.webApplication = webApplication
    }
    def title(String pageTitle){
        println("Calling title method in WebPageBuilder with ${pageTitle}")
        this._title = pageTitle
    }

    def name(String pageName){
        println("Calling name method in WebPageBuilder with ${pageName}")
        this._name = pageName
    }

    def Header(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=HeaderBuilder) Closure closure){
        var header = new HeaderBuilder()
        def code = closure.rehydrate(header, this, this)//permet de définir que tous les appels de méthodes
        code.resolveStrategy = Closure.DELEGATE_ONLY//à l'intérieur de la closure seront résolus en utilisant le delegate
        code()
        println "Building header ${header.build()}"
        this.componentList.addAll(new Header( header.build()))

    }

    WebPage buildPage(){
        var webPage = new WebPage()

        webPage.name = this._name

        webPage.title = this._title
        webPage.componentList = componentList
        return webPage
    }

}

class HeaderBuilder implements  GenericBuilder{

}


class UXifier extends  Script{
    WebApplication webApplication = new WebApplication()

    @Override
    Object run() {
        return null
    }

    def WebApplication(@DelegatesTo(WebApplicationBuilder) Closure closure){
        var app= new WebApplicationBuilder(webApplication)
        closure.delegate = app
        closure()

        var application = app.build()

        println application

        var applicationVisitor = new ApplicationModelVisitorVueJS()

        applicationVisitor.visit(application)

        println applicationVisitor.vueProject
        applicationVisitor.vueProject.toCode()


    }


}

trait GenericBuilder{

    List<Component> componentList = new ArrayList<>()

    def HorizontalLayout(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=HorizontalLayoutBuilder) Closure closure){
        var layoutBuilder =  new HorizontalLayoutBuilder()
        def code = closure.rehydrate(layoutBuilder, this,this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()

        this.componentList.addAll(new HorizontalLayout( layoutBuilder.build()))

    }

    def SocialMediaGroup(@DelegatesTo(SocialMediaGroupBuiler) Closure closure){
        var socialMediaGroupBuilder = new SocialMediaGroupBuiler()
        def code = closure.rehydrate(socialMediaGroupBuilder, this, this)//permet de définir que tous les appels de méthodes
        code.resolveStrategy = Closure.DELEGATE_ONLY//à l'intérieur de la closure seront résolus en utilisant le delegate
        code()

        this.componentList.addAll(new SocialMediaGroup(socialMediaGroupBuilder.build()))
    }


    def Catalog(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=CatalogBuilder) Closure closure){
        var catalogBuilder = new CatalogBuilder()
        def  code = closure.rehydrate(catalogBuilder, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        this.componentList.addAll(new SocialMediaGroup(catalogBuilder.build()))
    }

    List<Component> build(){
        return componentList
    }
}

class CatalogBuilder implements GenericBuilder {
    Catalog catalog = new Catalog()


    def Product(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=ProductBuilder) Closure closure) {
        var productBuilder = new ProductBuilder()
        def  code = closure.rehydrate(productBuilder, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
        this.componentList.add(productBuilder.buildProduct())
    }

    def Filter(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=FilterBuilder) Closure closure){
        var filterBuilder = new FilterBuilder()
        def  code = closure.rehydrate(filterBuilder, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
        this.componentList.add(new Filter(filterBuilder.build()))
    }

}


class ProductBuilder {

    Product product = new Product()

    def buildProduct(){
        return this.product
    }

    def Rating(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=RatingBuilder) Closure closure){
        var ratingBuilder = new RatingBuilder()
        def  code = closure.rehydrate(ratingBuilder, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
        this.product.setRating(ratingBuilder.build())
    }
}

class RatingBuilder {

    Rating rating = new Rating()

    final RatingType Stars = RatingType.Stars
    final RatingType Bar = RatingType.Bar
    final RatingType Mark = RatingType.Mark

    def ratingType(RatingType type){
        this.rating.ratingType = type
    }

    Rating build(){
        return this.rating
    }

}


class FilterBuilder implements GenericBuilder{



    def PriceFilter (@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=PriceFilterBuilder) Closure closure){
        var priceFilterBuilder = new PriceFilterBuilder()
        def  code = closure.rehydrate(priceFilterBuilder, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
        this.componentList.add(priceFilterBuilder.build())
    }

    def GenericFilter (@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=GenericFilterBuilder) Closure closure){
        var genericFilterBuilder = new GenericFilterBuilder()
        def  code = closure.rehydrate(genericFilterBuilder, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
        this.componentList.add(genericFilterBuilder.build())
    }

}

class PriceFilterBuilder {
    PriceFilter priceFilter = new PriceFilter()

    final PriceType Range = PriceType.Range
    final PriceType Bar = PriceType.Bar

    def priceType(PriceType type){
        this.priceFilter.priceType = type
    }

    PriceFilter build(){
        return this.priceFilter
    }
}


class GenericFilterBuilder {

    GenericFilter genericFilter = new GenericFilter()

    def targetAtributName(String name){
        this.genericFilter.targetAtributName = name
    }

    def targetAtributType(String type){
        this.genericFilter.targetAtributType = type
    }

    GenericFilter build(){
        return this.genericFilter
    }

}




class SocialMediaGroupBuiler implements  GenericBuilder{
    def SocialMedia(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=SocialMediaBuilder) Closure closure){
        var socialMediaBuilder = new SocialMediaBuilder()
        def code = closure.rehydrate(socialMediaBuilder, this, this)//permet de définir que tous les appels de méthodes
        code.resolveStrategy = Closure.DELEGATE_FIRST//à l'intérieur de la closure seront résolus en utilisant le delegate
        code()
        this.componentList.add(socialMediaBuilder.build())

    }
}

class SocialMediaBuilder {
    SocialMedia socialMedia = new SocialMedia()

    final SocialMediaType Facebook = SocialMediaType.Facebook
    final SocialMediaType Pinterest = SocialMediaType.Pinterest
    final SocialMediaType Instagram = SocialMediaType.Instagram
    final SocialMediaType LinkedIn = SocialMediaType.LinkedIn

    def type(SocialMediaType socialMediaType ){
        this.socialMedia.type  = socialMediaType
    }

    def url(String urlLink){
        this.socialMedia.url = urlLink
    }

    SocialMedia build(){
        return this.socialMedia
    }
}
class HorizontalLayoutBuilder implements GenericBuilder{

}
