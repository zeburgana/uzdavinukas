import java.io.*
import java.util.Random

/**
 * Created by Pug b0iiiii on 2017-11-21.
 */
static void main(String[] args) {
    String inputPath = System.getProperty("user.dir").substring(0 , System.getProperty("user.dir").length() - 3) + "resources\\data.txt"
    String outputPath = System.getProperty("user.dir").substring(0 , System.getProperty("user.dir").length() - 3) + "resources\\rez.txt"
    File file = new File(outputPath)
    file.text = ""
    Random rando = new Random()
    int testNumber = rando.nextInt(10) +1

    for(int i = 0; i<testNumber; i++){
        file << "testas nr. " + (i+1) + "\r\n"
        generateTest(inputPath)
        task(inputPath, outputPath)
    }


}
static void generateTest(String inputPath){
    String[] words = ["labas", "kreivas", "sleivas", "ponas", "takeliuotas", "šiuolaikiškas", "Žodis", "žodis",
                      "iki", "kratalynė", "patalyne", "kaldra", "buteliukas", "butelis", "darbas", "konkorėžis", "balandynas"]
    File file = new File(inputPath)
    Random random = new Random()
    int numberOfLetters = random.nextInt(10) + 5
    String line = ""

    for (int i = 0; i<5; i++){
        line += words[random.nextInt(words.length)] + " "
    }

    file.text = line + "\r\n" + numberOfLetters
}
static void task(String pathIn, String pathOut){

    String fileContents = readFile(pathIn)
    String[] lines = fileContents.split("\r\n")
    String words = lines[0]
    int limit = lines[1].toInteger()
    String formattedText = formattingText(words, limit)
    formattedText = validateWrittenFile(formattedText, limit)
    writeFile(pathOut, formattedText)

}
static String readFile(String path){
    return new File(path).text
}
static String formattingText(String words, int limit){
    def output = ""
    String[] wholeWords = words.split(" ")
    int tempLimit = limit
    String line = ""

    for (int i = 0; i<(wholeWords.length); i++){
        if(wholeWords[i].length() + 1 > tempLimit && line!="") {
            tempLimit = limit
            output += "\r\n"
            line = ""
        }

        if(tempLimit - wholeWords[i].length() < 0){
            line += wholeWords[i].substring(0, tempLimit) + "\r\n"
            wholeWords[i] = wholeWords[i].substring(tempLimit, wholeWords[i].length())
//            line += wholeWords[i] + "\r\n"
        }

        if(tempLimit - wholeWords[i].length() > 0 ) {
            line += wholeWords[i] + " "
            tempLimit -= (wholeWords[i].length() + 1)
        }

//        if(tempLimit<=3)
//            line += "\r\n"
        if(i==0)
            output = line
        else
            output += line
    }
    return output
}
static void writeFile(String path, String contents){
    new File(path).createNewFile()
    def file = new File(path)
    file << contents
}
static String validateWrittenFile(String text, int limit){
    String[] formattedLines = text.split("\r\n")
    int tmp = 0;

    for(int i = 0;i<(formattedLines.length);i++) {
        if (formattedLines[i].length() > limit)
            tmp++
    }
    if (tmp > 0)
        text += "\r\n\tnepraėjo, įvyko klaida(raidžių skaičius = " + limit + ")\r\n"
    else {
        text += "\r\n\tpraėjo, raidžių skaičius = " + limit + "\r\n"
    }
    text += "\r\n"
    return text
}