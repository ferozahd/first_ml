import markdown
from weasyprint import HTML

with open("domain-driven-design.md", "r") as file:
    text = file.read()

html = markdown.markdown(text)

HTML(string=html).write_pdf("output.pdf")