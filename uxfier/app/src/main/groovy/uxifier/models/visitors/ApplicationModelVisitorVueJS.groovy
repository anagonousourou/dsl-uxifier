package uxifier.models.visitors

import uxifier.models.ApplicationModelVisitor
import uxifier.models.Cart
import uxifier.models.Component
import uxifier.models.Header
import uxifier.models.HorizontalLayout
import uxifier.models.Menu
import uxifier.models.NavigationMenu
import uxifier.models.SocialMedia
import uxifier.models.SocialMediaGroup
import uxifier.models.WebApplication
import uxifier.models.WebPage
import uxifier.vue.project.models.VueComponent
import uxifier.vue.project.models.VueGeneratable
import uxifier.vue.project.models.VueJsCart
import uxifier.vue.project.models.VueJsSocialMedia
import uxifier.vue.project.models.VueJsSocialMediaGroup
import uxifier.vue.project.models.VueMenu
import uxifier.vue.project.models.VueMenuBar
import uxifier.vue.project.models.VueProject

class ApplicationModelVisitorVueJS implements ApplicationModelVisitor {
    int count = 1

    VueProject vueProject = new VueProject()

    private VueGeneratable parent

    @Override
    def visit(SocialMedia media) {
        var tmp = new VueJsSocialMedia(media.type.toString(), media.url)
        this.parent.addContent(tmp)
    }


    @Override
    def visit(HorizontalLayout layout) {
        return null
    }

    @Override
    def visit(Component component) {
        return null
    }

    @Override
    def visit(SocialMediaGroup socialMediaGroup) {
        var tmp = new VueJsSocialMediaGroup()

        this.parent.addContent(tmp)
        this.parent  = tmp

        socialMediaGroup.componentList.forEach(c -> c.accept(this))
    }

    @Override
    def visit(Cart cart) {

        var tmp = new VueJsCart()
        this.parent.addContent(tmp)
        this.parent  = tmp
        //cart.accept(this)
    }

    @Override
    def visit(Header header) {
        return null
    }

    @Override
    def visit(WebApplication application) {
        //idée plusieurs passages dans l'arbre, premier passage : trouver les dependencies et set certaines informations triviales,
        //passages supplémentaires pour résoudre des liens, du routing ...
        this.vueProject.name = application.name
        this.vueProject.packageJson.name = application.name

        for (WebPage webPage : application.pages) {
            webPage.accept(this)
        }

    }

    @Override
    def visit(WebPage webPage) {

        VueComponent vueComponent = new VueComponent()
        vueComponent.name = webPage.name

        var previousParent = this.parent
        this.parent = vueComponent

        for (Component component1 : webPage.getComponentList()) {
            component1.accept(this)
        }

        this.vueProject.addVueComponent(vueComponent)

        //Given the component is a webpage (the only one for now) we add it as content of App.vue

        this.vueProject.sourceDirectory.appFile.content.add(vueComponent)

        this.parent = previousParent
    }

    def visit(NavigationMenu navigationMenu){
        VueMenuBar menuBar = new VueMenuBar()

        var tmp = this.parent
        this.parent = menuBar

        for(Component comp : navigationMenu.componentList){
            comp.accept(this)
        }

        this.vueProject.sourceDirectory.appFile.content.add(menuBar)

        this.parent = tmp

        this.vueProject.packageJson.dependencies.put('@vaadin/vaadin-core','22.0.5')
    }

    @Override
    def visit(Menu menu) {
        VueMenu vueMenu =  new VueMenu()
        vueMenu.link = menu.link
        vueMenu.label = menu.label
        this.parent.addContent(vueMenu)
    }


}
