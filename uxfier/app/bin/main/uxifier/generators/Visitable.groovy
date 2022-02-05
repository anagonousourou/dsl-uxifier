package generators

interface Visitable {
    void accept(WebApplicationVisitor visitor)
}