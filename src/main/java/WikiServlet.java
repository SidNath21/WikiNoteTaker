import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;

/**
 * Class used to link search bar to website slides
 */
@WebServlet(name = "WikiServlet", urlPatterns = {"/wiki"})
public class WikiServlet extends HttpServlet {
    private static String lastHTMLCode = "";
    private static final String HTML_END = "\n" +
            "            updateSlides();\n" +
            "\n" +
            "        }\n" +
            "    </script>\n" +
            "</body>\n" +
            "</html>\n" +
            "\n";

    /**
     * Displays the website showing the slides
     * @param request gets the input stream of data, which is the value of the search bar
     * @param response gets the output stream to print HTML code to
     * @throws IOException if the request or response are invalid
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String s = request.getParameter("title");
        PrintWriter out = response.getWriter();
        lastHTMLCode = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <link rel = \"stylesheet\" type = \"text/css\" href = \"css/slides.css\" />\n" +
                "    <style>\n" +
                "        @import url('//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css');\n" +
                "        body{\n" +
                "            font-family: Arial, Helvetica, sans-serif;\n" +
                "            background-color: black;\n" +
                "            color: white;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        h1{\n" +
                "            text-transform: uppercase;\n" +
                "            font-size: 48px;\n" +
                "            line-height: 0;\n" +
                "            line-height: 1.5;\n" +
                "        }\n" +
                "\n" +
                "        h2{\n" +
                "            margin-top: 2em;\n" +
                "            line-height: 1;\n" +
                "            font-size: 32px;\n" +
                "        }\n" +
                "\n" +
                "        #container{\n" +
                "\n" +
                "            margin: 1em auto;\n" +
                "            width: 75%;\n" +
                "            min-width: 450px;\n" +
                "            border: 15px solid red;\n" +
                "            background-color: tomato;\n" +
                "            border-radius: 10px;\n" +
                "            display: flex;\n" +
                "            flex-direction: column;\n" +
                "            padding-bottom: 2em;\n" +
                "        }\n" +
                "\n" +
                "        .introSlide{\n" +
                "            text-align: center;\n" +
                "            margin: 2em auto;\n" +
                "            width: 90%;\n" +
                "            border: 10px solid maroon;\n" +
                "            border-radius: 5px;\n" +
                "            background-color: black ;\n" +
                "            line-height: 1.5;\n" +
                "            color: white;\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .introSlideTitle{\n" +
                "            font-size: 5vw;\n" +
                "\n" +
                "            padding: 1em ;\n" +
                "            text-align: center;\n" +
                "            text-transform: uppercase;\n" +
                "        }\n" +
                "\n" +
                "        .slide{\n" +
                "            text-align: center;\n" +
                "            margin: 2em auto;\n" +
                "            width: 90%;\n" +
                "            border: 10px solid black;\n" +
                "            border-radius: 5px;\n" +
                "            background-color: #191919 ;\n" +
                "            line-height: 1.5;\n" +
                "            color: white;\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .slideHeading{\n" +
                "\n" +
                "            font-size: 24px;\n" +
                "            border: 2px solid white;\n" +
                "            padding: 1em ;\n" +
                "            outline: none;\n" +
                "        }\n" +
                "\n" +
                "        .content{\n" +
                "            font-size: 18px;\n" +
                "            text-align: left;\n" +
                "            border: 2px solid white;\n" +
                "            outline: none;\n" +
                "        }\n" +
                "\n" +
                "        .settings{\n" +
                "            border: 2px solid white;\n" +
                "            display: flex;\n" +
                "            flex-direction: row;\n" +
                "            justify-content: space-evenly;\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        .deleteButton{\n" +
                "\n" +
                "            width: 75px;\n" +
                "            height: 50px;\n" +
                "\n" +
                "            margin: 1em;\n" +
                "\n" +
                "            text-align: center;\n" +
                "            font-weight: bolder;\n" +
                "            font-size: 1em;\n" +
                "\n" +
                "            background: none;\n" +
                "            border: 3px solid red;\n" +
                "            color: red;\n" +
                "\n" +
                "            cursor: pointer;\n" +
                "            outline: none;\n" +
                "            transition:  0.25s linear;\n" +
                "\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        .deleteButton:hover{\n" +
                "            color: white;\n" +
                "            background-color: red;\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        .editButton{\n" +
                "\n" +
                "            width: 75px;\n" +
                "            height: 50px;\n" +
                "\n" +
                "            margin: 1em;\n" +
                "            text-transform: uppercase;\n" +
                "            font-size: 1em;\n" +
                "            text-align: center;\n" +
                "            font-weight: bold;\n" +
                "\n" +
                "            background: none;\n" +
                "            border: 3px solid #2ecc71;\n" +
                "            color: #2ecc71;\n" +
                "\n" +
                "            cursor: pointer;\n" +
                "            outline: none;\n" +
                "            transition:  0.25s linear;\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        .editButton:hover{\n" +
                "            color: white;\n" +
                "            background: #2ecc71;\n" +
                "        }\n" +
                "\n" +
                "        .addButton{\n" +
                "\n" +
                "            width: 50%;\n" +
                "            height: 50px;\n" +
                "\n" +
                "\n" +
                "\n" +
                "            outline: none;\n" +
                "            background: none;\n" +
                "            cursor: pointer;\n" +
                "            margin: auto;\n" +
                "            font-size: 18px;\n" +
                "            text-transform: uppercase;\n" +
                "            font-weight: bold;\n" +
                "            color: grey;\n" +
                "            border: 5px solid #00ffff;\n" +
                "            border-radius: 15px;\n" +
                "            background-color: #121212;\n" +
                "            transition: 0.25s;\n" +
                "        }\n" +
                "\n" +
                "        .addButton:hover{\n" +
                "            color: white;\n" +
                "        }\n" +
                "\n" +
                "        #download img{\n" +
                "            margin: 1em auto 3em auto;\n" +
                "            width: 200px;\n" +
                "            opacity: 50%;\n" +
                "            transition: 0.5s;\n" +
                "        }\n" +
                "\n" +
                "        #download img:hover{\n" +
                "            opacity: 100%;\n" +
                "\n" +
                "        }\n" +
                "    </style>\n" +
                "    <title> Notes </title>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1 id = \"title\"> </h1>\n" +
                "\n" +
                "<div id = \"container\">  </div>\n" +
                "\n" +
                "<form method=\"post\" action=\"pdf\" id = \"form\" autocomplete=\"off\">\n" +
                "    <h2> Download Notes </h2>\n" +
                "    <div id = \"download\"> </div>\n" +
                "    <div id = \"submit\">\n" +
                "        <button> Create Notes </button> <br>\n" +
                "    </div>\n" +
                "</form>\n" +
                "\n" +
                "    <script>\n" +
                "        const container = document.querySelector(\"#container\");\n" +
                "        const title = document.querySelector(\"#title\");\n" +
                "        const downloadButton = document.querySelector(\"#download\");\n" +
                "\n" +
                "        let titleString = decodeURIComponent(window.location.search);\n" +
                "        titleString = titleString.substring(titleString.indexOf(\"=\") + 1);\n" +
                "        title.textContent = \"WIKI Notes\";\n" +
                "\n" +
                "        downloadButton.addEventListener(\"click\", function(){\n" +
                "\n" +
                "\n" +
                "\n" +
                "        });\n" +
                "\n" +
                "        let slides = [];\n" +
                "\n" +
                "        createTitleSlide();\n" +
                "        createSampleSlides();\n" +
                "\n" +
                "        function Slide(heading, body, rendered){\n" +
                "            this.heading = heading;\n" +
                "            this.body = body; // paragraph text\n" +
                "            this.rendered = false;\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "        function updateSlides(){\n" +
                "\n" +
                "            for(let i=0; i<slides.length; i++){\n" +
                "\n" +
                "                if(!slides[i].rendered){\n" +
                "\n" +
                "                    if(i!=0){\n" +
                "\n" +
                "                        const slide = document.createElement(\"div\");\n" +
                "                        slide.classList = \"slide\";\n" +
                "                        slide.value = i;\n" +
                "\n" +
                "                        let slideTitle = slides[i].heading;\n" +
                "                        let slideBody = slides[i].body;\n" +
                "\n" +
                "                        // parse slide body text\n" +
                "                        slideBody.replace(\"*\", \".\");\n" +
                "                        slideBody.replace('\"', \"'\");\n" +
                "                        sentences = slideBody.split(\". \");\n" +
                "\n" +
                "                        //create slide heading\n" +
                "                        const sildeHeading = document.createElement(\"div\");\n" +
                "                        sildeHeading.textContent = slideTitle;\n" +
                "                        sildeHeading.classList = \"slideHeading\";\n" +
                "\n" +
                "                        // create slide content\n" +
                "                        const content = document.createElement(\"div\");\n" +
                "                        content.classList = \"content\";\n" +
                "                        const list = document.createElement(\"ul\");\n" +
                "\n" +
                "                        // make bulletpoints\n" +
                "                        for(let j=0; j<sentences.length; j++){\n" +
                "                            const bullet = document.createElement(\"li\");\n" +
                "                            bullet.classList = \"bullet\";\n" +
                "                            bullet.textContent = sentences[j];\n" +
                "                            if(bullet.textContent != \"\") list.appendChild(bullet);\n" +
                "                        }\n" +
                "\n" +
                "\n" +
                "\n" +
                "                        content.appendChild(list);\n" +
                "\n" +
                "\n" +
                "\n" +
                "                        const settings = document.createElement(\"div\");\n" +
                "                        settings.classList = \"settings\";\n" +
                "\n" +
                "                        const deleteButton = document.createElement(\"button\");\n" +
                "                        deleteButton.classList = \"deleteButton\";\n" +
                "                        deleteButton.textContent = \"X\";\n" +
                "                        deleteButton.addEventListener(\"click\", function() {\n" +
                "\n" +
                "                            let c = confirm(\"Delete \" + sildeHeading.textContent + \" slide? \");\n" +
                "\n" +
                "                            if(c){\n" +
                "                                container.removeChild(slide);\n" +
                "                                container.removeChild(addButton);\n" +
                "                                slides.splice(i, 1);\n" +
                "                                updateSlides();\n" +
                "                            }\n" +
                "\n" +
                "                        });\n" +
                "\n" +
                "\n" +
                "\n" +
                "                        const editButton = document.createElement(\"button\");\n" +
                "                        editButton.classList = \"editButton\";\n" +
                "                        editButton.textContent = \"Edit\";\n" +
                "\n" +
                "                        editButton.addEventListener(\"click\", function() {\n" +
                "                            if(editButton.textContent == \"Edit\"){\n" +
                "                                slide.style.borderColor = \"#2ecc71\";\n" +
                "                                content.contentEditable = \"true\";\n" +
                "                                sildeHeading.contentEditable = \"true\";\n" +
                "                                editButton.textContent = \"Save\";\n" +
                "                            }\n" +
                "                            else if(editButton.textContent == \"Save\" ){\n" +
                "                                slide.style.borderColor = \"black\"\n" +
                "                                content.contentEditable = \"false\";\n" +
                "                                sildeHeading.contentEditable = \"false\";\n" +
                "                                editButton.textContent = \"Edit\";\n" +
                "\n" +
                "                                slides[i].heading = sildeHeading.textContent;\n" +
                "                                console.log(slides[i].body);\n" +
                "                                slides[i].body = \"\";\n" +
                "                                for(let j=0; j<list.childNodes.length; j++) slides[i].body += list.childNodes[j].textContent + \". \"; }\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "                        });\n" +
                "\n" +
                "                        settings.appendChild(deleteButton);\n" +
                "                        settings.appendChild(editButton);\n" +
                "\n" +
                "                        slide.appendChild(sildeHeading);\n" +
                "                        slide.appendChild(content);\n" +
                "                        slide.appendChild(settings);\n" +
                "\n" +
                "                        container.appendChild(slide);\n" +
                "\n" +
                "                    }\n" +
                "\n" +
                "\n" +
                "                    const addButton = document.createElement(\"button\");\n" +
                "                    addButton.classList = \"addButton\";\n" +
                "                    addButton.textContent = \"Add Slide\";\n" +
                "\n" +
                "                    addButton.addEventListener(\"click\", function(){\n" +
                "\n" +
                "                        let count = container.childNodes.length;\n" +
                "\n" +
                "                        while(count > 2){\n" +
                "                            container.removeChild(container.lastChild);\n" +
                "                            count--;\n" +
                "                        }\n" +
                "\n" +
                "                        for(let j=0; j<slides.length; j++) slides[j].rendered = false;\n" +
                "                        slides.splice(i+1, 0,  new Slide(\" \", \" \", false));\n" +
                "                        updateSlides();\n" +
                "\n" +
                "                    });\n" +
                "\n" +
                "                    container.appendChild(addButton);\n" +
                "                    slides[i].rendered = true;\n" +
                "\n" +
                "\n" +
                "\n" +
                "                }\n" +
                "            }\n" +
                "            // console.log(slides);\n" +
                "\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "        function createTitleSlide(){\n" +
                "\n" +
                "            const introSlide = document.createElement(\"div\");\n" +
                "            introSlide.classList = \"introSlide\";\n" +
                "            container.appendChild(introSlide);\n" +
                "\n" +
                "            const introSlideTitle = document.createElement(\"div\");\n" +
                "            introSlideTitle.classList = \"introSlideTitle\";\n" +
                "            introSlideTitle.textContent = \"" + s + "\";\n" +
                "            introSlide.appendChild(introSlideTitle);\n" +
                "\n" +
                "            slides.push(introSlide);\n" +
                "            container.appendChild(introSlide);\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "        function createSampleSlides(){\n" +
                "\n";
                HashMap<String, String> slideContent = Filterer.getValues(s);
                System.out.println(slideContent.size());
                for(String value: slideContent.keySet()){
                    lastHTMLCode += "slides.push(new Slide(\"" + value + "\", \"" + slideContent.get(value) + "\", false ));";
                }
                lastHTMLCode += HTML_END;

        out.println(lastHTMLCode);
        out.flush();
    }

    /**
     * Returns the last value of the HTML code used on the slides website as a string
     * Used to generate the PDF
     * @return String of HTML code
     */
    public static String getLastHTMLCode(){
        return lastHTMLCode;
    }
}