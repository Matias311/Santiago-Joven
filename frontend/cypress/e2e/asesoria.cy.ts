describe("Página de Asesoría", () => {
  it("debe cargar la pagina de asesoria directamente", () => {
    cy.visit("/asesoria");
    cy.get("h1").should("exist");
  });
});
