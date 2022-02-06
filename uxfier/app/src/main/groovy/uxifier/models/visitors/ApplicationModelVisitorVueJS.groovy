package uxifier.models.visitors

import uxifier.models.ApplicationModelVisitor
import uxifier.models.Component
import uxifier.models.Header
import uxifier.models.HorizontalLayout
import uxifier.models.SocialMedia
import uxifier.models.SocialMediaGroup
import uxifier.models.WebApplication
import uxifier.models.WebPage
import uxifier.vue.project.models.VueComponent
import uxifier.vue.project.models.VueProject

class ApplicationModelVisitorVueJS implements  ApplicationModelVisitor{
    int count = 1

    VueProject vueProject=new VueProject();

    @Override
    def visit(SocialMedia media) {
        return null
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
        return null
    }

    @Override
    def visit(Header header) {
        return null
    }

    @Override
    def visit(WebApplication application) {

        //premier pass : trouver les dependencies et set certaines informations
        this.vueProject.name = application.name
        this.vueProject.packageJson.name = application.name

        for(WebPage webPage : application.pages){
            webPage.accept(this)
        }
        return null
    }

    @Override
    def visit(WebPage webPage) {
        VueComponent vueComponent = new VueComponent()
        vueComponent.name = webPage.name
    }
}
