/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package uxifier

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import uxifier.models.Component
import uxifier.models.Field
import uxifier.models.FieldGroup
import uxifier.models.Form
import uxifier.models.Header
import uxifier.models.HorizontalLayout
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

class ScriptInterpreter {
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

class WebApplicationBuilder {

    WebApplication webApplication

    WebApplicationBuilder(WebApplication webApplication) {
        this.webApplication = webApplication
    }

    def name(String appName) {
        if (appName.contains(' ')) {
            println "Invalid name for application '${appName}' : it should not have spaces"
            System.exit(1)
        }
        this.webApplication.name = appName
    }

    def build() {
        return this.webApplication
    }

    def WebPage(@DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = WebPageBuilder) Closure closure) {
        var webPageBuilder = new WebPageBuilder(webApplication)
        def code = closure.rehydrate(webPageBuilder, this, this)//permet de définir que tous les appels de méthodes
        code.resolveStrategy = Closure.DELEGATE_ONLY
//à l'intérieur de la closure seront résolus en utilisant le delegate
        code()
        this.webApplication.addWebPage(webPageBuilder.buildPage())
    }
}

class WebPageBuilder implements GenericBuilder {
    String _title
    String _name
    WebApplication webApplication

    WebPageBuilder(WebApplication webApplication) {
        this.webApplication = webApplication
    }

    def title(String pageTitle) {
        this._title = pageTitle
    }

    def name(String pageName) {
        this._name = pageName
    }

    def Header(@DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = HeaderBuilder) Closure closure) {
        var header = new HeaderBuilder()
        def code = closure.rehydrate(header, this, this)//permet de définir que tous les appels de méthodes
        code.resolveStrategy = Closure.DELEGATE_ONLY
//à l'intérieur de la closure seront résolus en utilisant le delegate
        code()
        this.componentList.addAll(new Header(header.build()))

    }

    WebPage buildPage() {
        var webPage = new WebPage()

        webPage.name = this._name

        webPage.title = this._title
        webPage.componentList = componentList
        return webPage
    }

}

class HeaderBuilder implements GenericBuilder {

}


class UXifier extends Script {
    WebApplication webApplication = new WebApplication()

    @Override
    Object run() {
        return null
    }

    def WebApplication(@DelegatesTo(WebApplicationBuilder) Closure closure) {
        var app = new WebApplicationBuilder(webApplication)
        closure.delegate = app
        closure()

        var application = app.build()
        println "application : " + application

        var applicationVisitor = new ApplicationModelVisitorVueJS()

        applicationVisitor.visit(application)

        println applicationVisitor.vueProject
        applicationVisitor.vueProject.toCode()


    }


}

trait GenericBuilder {

    List<Component> componentList = new ArrayList<>()

    def HorizontalLayout(@DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = HorizontalLayoutBuilder) Closure closure) {
        var layoutBuilder = new HorizontalLayoutBuilder()
        def code = closure.rehydrate(layoutBuilder, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()

        this.componentList.addAll(new HorizontalLayout(layoutBuilder.build()))

    }

    def SocialMediaGroup(@DelegatesTo(SocialMediaGroupBuiler) Closure closure) {
        var socialMediaGroupBuilder = new SocialMediaGroupBuiler()
        def code = closure.rehydrate(socialMediaGroupBuilder, this, this)
//permet de définir que tous les appels de méthodes
        code.resolveStrategy = Closure.DELEGATE_ONLY
//à l'intérieur de la closure seront résolus en utilisant le delegate
        code()

        this.componentList.addAll(new SocialMediaGroup(socialMediaGroupBuilder.build()))
    }

    def Form(@DelegatesTo(FormBuilder) Closure closure){
        var formBuilder = new FormBuilder()
        def code = closure.rehydrate(formBuilder, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
        this.componentList.add(formBuilder.buildForm())
    }

    List<Component> build(){
        return componentList
    }
}

class SocialMediaGroupBuiler implements  GenericBuilder{
    def SocialMedia(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=SocialMediaBuilder) Closure closure){
        var socialMediaBuilder = new SocialMediaBuilder()
        def code = closure.rehydrate(socialMediaBuilder, this, this)//permet de définir que tous les appels de méthodes
        code.resolveStrategy = Closure.OWNER_FIRST
//à l'intérieur de la closure seront résolus en utilisant le delegate
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

    def type(SocialMediaType socialMediaType) {
        this.socialMedia.type = socialMediaType
    }

    def url(String urlLink) {
        this.socialMedia.url = urlLink
    }

    SocialMedia build() {
        return this.socialMedia
    }
}

class FormBuilder implements GenericBuilder{
    String _name

    def name(String name) {
        this._name = name
    }

    def FieldGroup(@DelegatesTo(FieldGroupBuilder) Closure closure){
        var fieldGroupBuilder = new FieldGroupBuilder()
        def code = closure.rehydrate(fieldGroupBuilder, this, this)
        code.resolveStrategy = Closure.OWNER_FIRST
        code()
        this.componentList.addAll(new FieldGroup(fieldGroupBuilder.build()))
    }

    Form buildForm() {
        var form = new Form()
        form.name = this._name
        form.componentList = componentList
        return form
    }
}


class FieldGroupBuilder implements GenericBuilder{
    def Field(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=FieldBuilder) Closure closure){
        var fieldBuilder = new FieldBuilder()
        def code = closure.rehydrate(fieldBuilder, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code()
        this.componentList.add(fieldBuilder.build())
    }
}


class FieldBuilder {
    Field field = new Field();

    def name(String name){
        this.field.name = name;
    }
    def type(String type){
        this.field.type = type;
    }
    Field build(){
        return this.field;
    }
}

class HorizontalLayoutBuilder implements GenericBuilder {


}