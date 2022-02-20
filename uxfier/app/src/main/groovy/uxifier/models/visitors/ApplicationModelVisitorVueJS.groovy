package uxifier.models.visitors

import uxifier.models.Accordion
import uxifier.models.AccordionGroup
import uxifier.models.Action
import uxifier.models.ActionMenuBar
import uxifier.models.ApplicationModelVisitor
import uxifier.models.CartAction
import uxifier.models.CartPreview
import uxifier.models.Component
import uxifier.models.Field
import uxifier.models.FieldGroup
import uxifier.models.Form
import uxifier.models.Header
import uxifier.models.HorizontalLayout
import uxifier.models.Menu
import uxifier.models.NavigationMenu
import uxifier.models.NavigationMenuType
import uxifier.models.SocialMedia
import uxifier.models.SocialMediaGroup
import uxifier.models.WebApplication
import uxifier.models.WebPage
import uxifier.vue.project.models.VueActionMenu
import uxifier.vue.project.models.VueActionMenuBar
import uxifier.vue.project.models.VueCartActionMenu
import uxifier.vue.project.models.VueCartPreview
import uxifier.vue.project.models.VueCartPreviewArticle
import uxifier.vue.project.models.VueComponent
import uxifier.vue.project.models.VueGeneratable
import uxifier.vue.project.models.VueJsAccordion
import uxifier.vue.project.models.VueJsAccordionGroup
import uxifier.vue.project.models.VueJsField
import uxifier.vue.project.models.VueJsForm
import uxifier.vue.project.models.VueJsSocialMedia
import uxifier.vue.project.models.VueJsSocialMediaGroup
import uxifier.vue.project.models.VueMenu
import uxifier.vue.project.models.VueMenuBar
import uxifier.vue.project.models.VueMenuItemNavbar
import uxifier.vue.project.models.VueMenuNavbar
import uxifier.vue.project.models.VueProject

class ApplicationModelVisitorVueJS implements  ApplicationModelVisitor{
    int count = 1

    VueProject vueProject=new VueProject()

    private VueGeneratable parent

    private NavigationMenuType currentNavigationMenuType = null

    @Override
    def visit(SocialMedia media) {
        var tmp = new VueJsSocialMedia(media.type.toString(), media.url)
        this.parent.addContent(tmp)
        return tmp
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
        this.parent  = tmp // Pourquoi cette ligne ?
        socialMediaGroup.componentList.forEach(c -> c.accept(this))
        return tmp
    }

    @Override
    def visit(Form form){
        var tmp = new VueJsForm()
        tmp.name = form.name
        for(Component c : form.componentList){
            if(c instanceof FieldGroup){
                for(Field f : (c.componentList as List<Field>)){
                    var tmpField = new VueJsField()
                    tmpField.setName(f.name)
                    tmpField.setType(f.type)
                    tmp.fields.add(tmpField)
                }
            }
        }
        this.parent.addContent(tmp)
        this.vueProject.packageJson.dependencies.put('@vaadin/vaadin-core','22.0.5')
        return tmp
    }

    def buildForm(Form form){
        var tmp = new VueJsForm()
        tmp.name = form.name
        for(Component c : form.componentList){
            if(c instanceof FieldGroup){
                for(Field f : (c.componentList as List<Field>)){
                    var tmpField = new VueJsField()
                    tmpField.setName(f.name)
                    tmpField.setType(f.type)
                    tmp.fields.add(tmpField)
                }
            }
        }
        return tmp
    }

    @Override
    def visit(Field field){
        return null
    }

    @Override
    def visit(AccordionGroup accordionGroup){
        var tmp = new VueJsAccordionGroup();

        for(Accordion a : (accordionGroup.componentList as List<Accordion>)){
            VueJsAccordion tmpAcc = new VueJsAccordion()
            tmpAcc.name = a.name

            for(Component c : a.componentList){

                if(c instanceof Form){
                    tmpAcc.components.add(buildForm(c))
                }
                //tmpAcc.components.add(c.accept(this) as VueGeneratable)

            }
            tmp.accordions.add(tmpAcc)
        }

        this.parent.addContent(tmp)
        this.vueProject.packageJson.dependencies.put('@vaadin/vaadin-core','22.0.5')
        return tmp
    }

    @Override
    def visit(Accordion accordion){
        return null
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
        this.vueProject.pageTitle = application.title
        this.vueProject.packageJson.name = application.name
        if(application.navigationMenu != null){
            application.navigationMenu.accept(this)
        }
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

    def visit(NavigationMenu navigationMenu) {
        this.currentNavigationMenuType = navigationMenu.menuType
        if (this.currentNavigationMenuType == NavigationMenuType.Navbar) {
            VueMenuNavbar menuNavbar = new VueMenuNavbar()
            menuNavbar.setApplicationName(navigationMenu.getApplicationName())
            this.parent = menuNavbar

            for (Component comp : navigationMenu.componentList) {
                comp.accept(this)
            }
            this.vueProject.sourceDirectory.appFile.content.add(menuNavbar)

        } else if (this.currentNavigationMenuType == NavigationMenuType.Drawer) {
            VueMenuBar menuBar = new VueMenuBar()
            menuBar.setApplicationName(navigationMenu.getApplicationName())

            this.parent = menuBar

            for (Component comp : navigationMenu.componentList) {
                comp.accept(this)
            }
            this.vueProject.sourceDirectory.appFile.content.add(menuBar)
        }
        this.vueProject.packageJson.dependencies.put('@vaadin/vaadin-core', '22.0.5')
    }

    @Override
    def visit(Menu menu) {
        if (this.currentNavigationMenuType == NavigationMenuType.Drawer) {
            VueMenu vueMenu = new VueMenu()
            vueMenu.link = menu.link
            vueMenu.label = menu.label
            vueMenu.icon = menu.icon
            this.parent.addContent(vueMenu)
        } else {
            VueMenuItemNavbar vueMenu = new VueMenuItemNavbar()
            vueMenu.link = menu.link
            vueMenu.label = menu.label
            this.parent.addContent(vueMenu)
        }

    }

    @Override
    def visit(Action action) {
        VueActionMenu actionMenu = new VueActionMenu()
        actionMenu.label = action.label
        this.parent.addContent(actionMenu)
        println("Adding action")
    }

    @Override
    def visit(ActionMenuBar menuBar) {

        println("Adding actionMenuBar")
        var previousParent = this.parent
        var vueActionMenuBar = new VueActionMenuBar()

        this.parent = vueActionMenuBar
        menuBar.componentList.forEach(c -> c.accept(this))

        this.parent = previousParent

        this.vueProject.sourceDirectory.appFile.content.add(vueActionMenuBar)

    }

    @Override
    def visit(CartAction action) {
        var vueAction = new VueCartActionMenu(action.label, action.displayCartCount, action.displayCartIcon)
        if(action.cartPreview != null){
            var previousAction = this.parent
            this.parent = vueAction
            action.cartPreview.accept(this)
            this.parent = previousAction
        }
        this.parent.addContent(vueAction)
        println("Adding cartaction")
    }

    @Override
    def visit(CartPreview cartPreview) {
        var vueCartPreview = new VueCartPreview()
        vueCartPreview.displayTotal = cartPreview.displayTotal

        vueCartPreview.setArticleInCartPreview(new VueCartPreviewArticle(cartPreview.articleInCartPreview))

        (this.parent as VueCartActionMenu ).setCartPreview(vueCartPreview)
    }

}
