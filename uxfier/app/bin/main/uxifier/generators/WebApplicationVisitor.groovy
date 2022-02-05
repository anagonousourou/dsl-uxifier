package generators

import models.WebApplication

interface WebApplicationVisitor {

    void visit(WebApplication application)
}