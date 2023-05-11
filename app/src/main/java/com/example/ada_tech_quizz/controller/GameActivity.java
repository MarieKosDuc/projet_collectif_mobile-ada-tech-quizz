package com.example.ada_tech_quizz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ada_tech_quizz.R;
import com.example.ada_tech_quizz.model.Question;
import com.example.ada_tech_quizz.model.QuestionBank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    // initialization of the member variables
    private TextView mQuestionText;
    private Button mButton1, mButton2, mButton3, mButton4;
    private QuestionBank mQuestionBank = initializeQuestionBank();

    private int mScore = 0, mQuestionNumber = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // plugging the variables into the layout components
        mQuestionText = findViewById(R.id.game_activity_textview_question);
        mButton1 = findViewById(R.id.game_activity_button_1);
        mButton2 = findViewById(R.id.game_activity_button_2);
        mButton3 = findViewById(R.id.game_activity_button_3);
        mButton4 = findViewById(R.id.game_activity_button_4);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name_key");

        displayQuestion(mQuestionBank.getCurrentQuestion());
    }

    // method to display a question
    private void displayQuestion(final Question question){
        // get text and answers for the question
        mQuestionText.setText(question.getQuestion());
        String[] mQuestionList = question.getChoiceList().toArray(new String[0]);

        // display text and answers, initialize buttons color and make them clickable
        // A REFACTORER !!
        mButton1.setText(mQuestionList[0]);
        mButton1.setBackgroundColor(Color.rgb(165, 105, 189));
        mButton1.setEnabled(true);
        mButton2.setText(mQuestionList[1]);
        mButton2.setBackgroundColor(Color.rgb(165, 105, 189));
        mButton2.setEnabled(true);
        mButton3.setText(mQuestionList[2]);
        mButton3.setBackgroundColor(Color.rgb(165, 105, 189));
        mButton3.setEnabled(true);
        mButton4.setText(mQuestionList[3]);
        mButton4.setBackgroundColor(Color.rgb(165, 105, 189));
        mButton4.setEnabled(true);
    }

    // onclick to verify the player's answer and go to the next question if correct
    @Override
    public void onClick(View v) {

        mQuestionNumber--;

        // creation of a hashmap to access the buttons
        Map<Integer, Button> buttonsMap = new HashMap<>();
        buttonsMap.put(0, mButton1);
        buttonsMap.put(1, mButton2);
        buttonsMap.put(2, mButton3);
        buttonsMap.put(3, mButton4);

        int index;

        // A REFACTORER avec un switch ?
        if(v == mButton1){
            index = 0;
        } else if(v == mButton2){
            index = 1;
        } else if(v == mButton3){
            index = 2;
        }else if(v == mButton4){
            index = 3;
        } else{
            throw new IllegalStateException("Unknown clicked view : " + v);
        }

        // set all buttons to red and disable them
        // A REFACTORER !!
        mButton1.setBackgroundColor(Color.rgb(232, 82, 63));
        mButton1.setEnabled(false);
        mButton2.setBackgroundColor(Color.rgb(232, 82, 63));
        mButton2.setEnabled(false);
        mButton3.setBackgroundColor(Color.rgb(232, 82, 63));
        mButton3.setEnabled(false);
        mButton4.setBackgroundColor(Color.rgb(232, 82, 63));
        mButton4.setEnabled(false);

        // set the correct answer to green

        buttonsMap.get(mQuestionBank.getCurrentQuestion().getAnswerIndex()).setBackgroundColor(Color.rgb(107, 195 , 109));

        // do something if answer is right
        if(index == mQuestionBank.getCurrentQuestion().getAnswerIndex()){
            mScore++;
            //v.setBackgroundColor(Color.rgb(38, 247, 13));

            Toast.makeText(this, "Correct! Score : " + String.valueOf(mScore) + " Questions : " + String.valueOf(mQuestionNumber), Toast.LENGTH_SHORT).show();

            // do something if answer is wrong
        } else {

            Toast.makeText(this, "Wrong! Score : " + String.valueOf(mScore) + " Questions : " + String.valueOf(mQuestionNumber), Toast.LENGTH_SHORT).show();
        }

        if(mQuestionNumber == 0){
            Toast.makeText(this, "Finish !", Toast.LENGTH_LONG).show();
        }

        // increment question index to get to next question
        mQuestionBank.getNextQuestion();

        // handler to generate a 2 second delay before displaying next question
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                displayQuestion(mQuestionBank.getCurrentQuestion());
            }
        }, 4000);

    }
    // method to generate a new questionBank
    private QuestionBank initializeQuestionBank(){
        List<Question> newQuestionList = new ArrayList<Question>();
        Question initQuestion = new Question("Are you ready to play?",
                Arrays.asList(
                        "Yes!",
                        "No...",
                        "Well...",
                        "Not sure..."
                ), 0);
        newQuestionList.add(initQuestion);

        Question question1 = new Question("Qu'est-ce que PHP signifie ?",
                Arrays.asList(
                        "Personal Hypertext Processor",
                        "PHP Hypertext Preprocessor",
                        "Private Home Page",
                        "Public Host Page"
                ), 1);
        newQuestionList.add(question1);


        Question question2 = new Question("Quelle est la particularité du langage PHP et ce qui le différencie de tous les autres langages de programmation ?",
                Arrays.asList(
                        "C'est un langage orienté objet",
                        "C'est un langage compilé",
                        "C'est un langage à typage dynamique",
                        "Le HTML peut être directement intégré dans le code PHP"
                ), 3);
        newQuestionList.add(question2);


        Question question3 = new Question("Quel est le framework Python pour la création d'interfaces graphiques ?",
                Arrays.asList(
                        "Flask",
                        "Django",
                        "Tkinter",
                        "Pyramid"
                ), 2);
        newQuestionList.add(question3);

        Question question4 = new Question("Quelle est la fonction SQL pour récupérer toutes les colonnes d'une table ?",
                Arrays.asList(
                        "SELECT * FROM table",
                        "GET ALL COLUMNS FROM table",
                        "FETCH table.*",
                        "SHOW ALL COLUMNS FROM table"
                ), 0);
        newQuestionList.add(question4);

        Question question5 = new Question("Quel est le nom du test qui vérifie qu'une application fonctionne comme prévu ?",
                Arrays.asList(
                        "Test unitaire",
                        "Test fonctionnel",
                        "Test de performance",
                        "Test d'intégration"
                ), 1);
        newQuestionList.add(question5);

        Question question6 = new Question("Quelle est la méthode JavaScript pour ajouter un élément à la fin d'un tableau ?",
                Arrays.asList(
                        "array.push()",
                        "array.unshift()",
                        "array.add()",
                        "array.append()"
                ), 0);
        newQuestionList.add(question6);

        Question question7 = new Question("Quelle est la fonction PHP pour se connecter à une base de données MySQL ?",
                Arrays.asList(
                        "mysql_connect()",
                        "mysqli_connect()",
                        "pdo_connect()",
                        "db_connect()"
                ), 1);
        newQuestionList.add(question7);

        Question question8 = new Question("Quel est le langage utilisé pour les requêtes SQL ?",
                Arrays.asList(
                        "Structured Query Language",
                        "Standard Query Language",
                        "Sequential Query Language",
                        "Simple Query Language"
                ), 0);
        newQuestionList.add(question8);

        Question question9 = new Question("Quel est le nom du framework JavaScript pour les applications frontales ?",
                Arrays.asList(
                        "React",
                        "Angular",
                        "Vue.js",
                        "Ember.js"
                ), 0);
        newQuestionList.add(question9);

        Question question10 = new Question("Quel est le nom du test qui vérifie qu'une application ne plante pas ?",
                Arrays.asList(
                        "Test de régression",
                        "Test de non-régression",
                        "Test de robustesse",
                        "Test de sécurité"
                ), 2);
        newQuestionList.add(question10);

        Question question11 = new Question("Quel est le symbole pour déclarer une variable en PHP ?",
                Arrays.asList(
                        "$",
                        "#",
                        "&",
                        "@"
                ), 0);
        newQuestionList.add(question11);

        Question question12 = new Question("Quel est le nom de la méthode Python pour supprimer un élément d'un dictionnaire ?",
                Arrays.asList(
                        "delete()",
                        "remove()",
                        "pop()",
                        "discard()"
                ), 2);
        newQuestionList.add(question12);

        Question question13 = new Question("Quel est le nom du framework CSS le plus populaire ?",
                Arrays.asList(
                        "Bootstrap",
                        "Foundation",
                        "Materialize",
                        "Bulma"
                ), 0);
        newQuestionList.add(question13);

        Question question14 = new Question("Quelle est la méthode PHP pour récupérer la méthode HTTP utilisée pour la requête ?",
                Arrays.asList(
                        "GET",
                        "POST",
                        "REQUEST_METHOD",
                        "HTTP_METHOD"
                ), 2);
        newQuestionList.add(question14);

        Question question15 = new Question("Quel est le nom de la méthode Python pour trier une liste ?",
                Arrays.asList(
                        "sort()",
                        "order()",
                        "arrange()",
                        "group()"
                ), 0);
        newQuestionList.add(question15);


        Question question16 = new Question("Quel est le nom du langage de programmation utilisé pour les scripts côté serveur en JavaScript ?",
                Arrays.asList(
                        "Node.js",
                        "JQuery",
                        "Angular",
                        "React"
                ), 0);
        newQuestionList.add(question16);


        Question question17 = new Question("Quel est le nom de la méthode PHP pour récupérer les données d'un formulaire HTML ?",
                Arrays.asList(
                        "$_GET",
                        "$_POST",
                        "$_REQUEST",
                        "$_SERVER"
                ), 1);
        newQuestionList.add(question17);

        Question question18 = new Question("Quel est le nom de la méthode Python pour récupérer la longueur d'une liste ?",
                Arrays.asList(
                        "size()",
                        "length()",
                        "count()",
                        "len()"
                ), 3);
        newQuestionList.add(question18);

        Question question19 = new Question("Quel est le nom du framework PHP pour les applications Web ?",
                Arrays.asList(
                        "Laravel",
                        "Symfony",
                        "CakePHP",
                        "CodeIgniter"
                ), 0);
        newQuestionList.add(question19);

        Question question20 = new Question("Quel est le nom du test qui vérifie qu'une application fonctionne correctement pour tous les utilisateurs ?",
                Arrays.asList(
                        "Test d'intégration",
                        "Test unitaire",
                        "Test de charge",
                        "Test de compatibilité"
                ), 3);
        newQuestionList.add(question20);

        Question question21 = new Question("Quel est le nom de la méthode Python pour ajouter un élément à une liste ?",
                Arrays.asList(
                        "push()",
                        "add()",
                        "insert()",
                        "append()"
                ), 3);
        newQuestionList.add(question21);

        Question question22 = new Question("Quel est le nom du framework JavaScript pour les applications Web monopage ?",
                Arrays.asList(
                        "Angular",
                        "React",
                        "Vue.js",
                        "Ember.js"
                ), 1);
        newQuestionList.add(question22);

        Question question23 = new Question("Quel est le nom du langage de requête pour les bases de données relationnelles ?",
                Arrays.asList(
                        "SQL",
                        "XML",
                        "JSON",
                        "HTML"
                ), 0);
        newQuestionList.add(question23);

        Question question24 = new Question("Quel est le nom de la méthode PHP pour rediriger l'utilisateur vers une autre page ?",
                Arrays.asList(
                        "go()",
                        "redirect()",
                        "header()",
                        "location()"
                ), 3);
        newQuestionList.add(question24);

        Question question25 = new Question("Quel est le nom du test qui vérifie qu'une fonction ou une méthode fonctionne correctement ?",
                Arrays.asList(
                        "Test de charge",
                        "Test unitaire",
                        "Test d'intégration",
                        "Test de performance"
                ), 1);
        newQuestionList.add(question25);

        Question question26 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour le développement de jeux vidéo ?",
                Arrays.asList(
                        "C++",
                        "Java",
                        "Python",
                        "Ruby"
                ), 0);
        newQuestionList.add(question26);

        Question question27 = new Question("Quel est le nom de la méthode Python pour supprimer un élément d'une liste ?",
                Arrays.asList(
                        "remove()",
                        "pop()",
                        "delete()",
                        "splice()"
                ), 0);
        newQuestionList.add(question27);

        Question question28 = new Question("Quel est le nom du framework PHP pour la création de sites e-commerce ?",
                Arrays.asList(
                        "Magento",
                        "Drupal",
                        "Joomla",
                        "WordPress"
                ), 0);
        newQuestionList.add(question28);

        Question question29 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour le développement de scripts côté serveur ?",
                Arrays.asList(
                        "JavaScript",
                        "Python",
                        "Ruby",
                        "PHP"
                ), 3);
        newQuestionList.add(question29);

        Question question30 = new Question("Quel est le nom du test qui vérifie qu'une application fonctionne correctement après une modification de code ?",
                Arrays.asList(
                        "Test de performance",
                        "Test unitaire",
                        "Test de régression",
                        "Test d'acceptation"
                ), 2);
        newQuestionList.add(question30);

        Question question31 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour la création de sites Web dynamiques ?",
                Arrays.asList(
                        "HTML",
                        "CSS",
                        "JavaScript",
                        "XML"
                ), 2);
        newQuestionList.add(question31);

        Question question32 = new Question("Quel est le nom de la méthode PHP pour récupérer les informations d'une requête HTTP ?",
                Arrays.asList(
                        "getHeaders()",
                        "getRequest()",
                        "getParams()",
                        "getInput()"
                ), 1);
        newQuestionList.add(question32);

        Question question33 = new Question("Quel est le nom du framework Python pour la création d'interfaces graphiques ?",
                Arrays.asList(
                        "Django",
                        "Flask",
                        "PyQt",
                        "Pyramid"
                ), 2);
        newQuestionList.add(question33);

        Question question34 = new Question("Quel est le nom de la méthode JavaScript pour créer un objet ?",
                Arrays.asList(
                        "newObject()",
                        "createObject()",
                        "makeObject()",
                        "Object.create()"
                ), 3);
        newQuestionList.add(question34);

        Question question35 = new Question("Quel est le nom du test qui vérifie qu'une application fonctionne correctement dans différents environnements ?",
                Arrays.asList(
                        "Test de performance",
                        "Test d'intégration",
                        "Test de compatibilité",
                        "Test de sécurité"
                ), 2);
        newQuestionList.add(question35);

        Question question36 = new Question("Quel est le nom de la méthode PHP pour récupérer les données d'un formulaire envoyé en POST ?",
                Arrays.asList(
                        "getFormData()",
                        "getPostData()",
                        "getValues()",
                        "$_POST"
                ), 3);
        newQuestionList.add(question36);

        Question question37 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour le traitement des données scientifiques et statistiques ?",
                Arrays.asList(
                        "Java",
                        "Python",
                        "Ruby",
                        "PHP"
                ), 1);
        newQuestionList.add(question37);

        Question question38 = new Question("Quel est le nom du framework JavaScript pour la création d'applications monopages ?",
                Arrays.asList(
                        "AngularJS",
                        "React",
                        "Vue.js",
                        "Ember.js"
                ), 1);
        newQuestionList.add(question38);

        Question question39 = new Question("Quel est le nom de la méthode Python pour ajouter un élément à une liste ?",
                Arrays.asList(
                        "add()",
                        "append()",
                        "insert()",
                        "push()"
                ), 1);
        newQuestionList.add(question39);

        Question question40 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour le développement de jeux en ligne ?",
                Arrays.asList(
                        "JavaScript",
                        "Ruby",
                        "Python",
                        "Java"
                ), 0);
        newQuestionList.add(question40);

        Question question41 = new Question("Quel est le nom du framework PHP pour la création de sites Web ?",
                Arrays.asList(
                        "Laravel",
                        "Symfony",
                        "CodeIgniter",
                        "Zend"
                ), 0);
        newQuestionList.add(question41);

        Question question42 = new Question("Quel est le nom de la méthode JavaScript pour ajouter un élément à un tableau ?",
                Arrays.asList(
                        "add()",
                        "push()",
                        "append()",
                        "insert()"
                ), 1);
        newQuestionList.add(question42);

        Question question43 = new Question("Quel est le nom du test qui vérifie qu'une application fonctionne correctement dans différentes langues et cultures ?",
                Arrays.asList(
                        "Test de compatibilité",
                        "Test de localisation",
                        "Test de sécurité",
                        "Test de charge"
                ), 1);
        newQuestionList.add(question43);

        Question question44 = new Question("Quel est le nom du framework Python pour le développement de jeux vidéo ?",
                Arrays.asList(
                        "PyGame",
                        "Django",
                        "Flask",
                        "PyQt"
                ), 0);
        newQuestionList.add(question44);

        Question question45 = new Question("Quel est le nom de la méthode PHP pour récupérer les données d'un formulaire envoyé en GET ?",
                Arrays.asList(
                        "$_POST",
                        "getValues()",
                        "getQueryData()",
                        "$_GET"
                ), 3);
        newQuestionList.add(question45);

        Question question46 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour la création de chatbots ?",
                Arrays.asList(
                        "JavaScript",
                        "Python",
                        "Ruby",
                        "PHP"
                ), 1);
        newQuestionList.add(question46);

        Question question48 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour la création d'applications mobiles ?",
                Arrays.asList(
                        "Python",
                        "Java",
                        "Ruby",
                        "PHP"
                ), 1);
        newQuestionList.add(question48);

        Question question49 = new Question("Quel est le nom du framework PHP pour la création de forums en ligne ?",
                Arrays.asList(
                        "Laravel",
                        "Symfony",
                        "CodeIgniter",
                        "phpBB"
                ), 3);
        newQuestionList.add(question49);

        Question question50 = new Question("Quel est le nom de la méthode Python pour enlever un élément d'une liste ?",
                Arrays.asList(
                        "remove()",
                        "pop()",
                        "delete()",
                        "shift()"
                ), 1);
        newQuestionList.add(question50);

        Question question51 = new Question("Quel est le nom du test qui vérifie la résistance d'une application face à une charge importante ?",
                Arrays.asList(
                        "Test de compatibilité",
                        "Test de localisation",
                        "Test de sécurité",
                        "Test de charge"
                ), 3);
        newQuestionList.add(question51);

        Question question52 = new Question("Quel est le nom du framework JavaScript pour la création d'applications mobiles ?",
                Arrays.asList(
                        "React Native",
                        "AngularJS",
                        "Ember.js",
                        "Vue.js"
                ), 0);
        newQuestionList.add(question52);

        Question question53 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour le développement de logiciels d'automatisation ?",
                Arrays.asList(
                        "Python",
                        "Java",
                        "Ruby",
                        "PHP"
                ), 0);
        newQuestionList.add(question53);

        Question question54 = new Question("Quel est le nom du framework PHP pour la création de boutiques en ligne ?",
                Arrays.asList(
                        "Laravel",
                        "Symfony",
                        "CodeIgniter",
                        "Magento"
                ), 3);
        newQuestionList.add(question54);

        Question question55 = new Question("Quel est le nom de la méthode JavaScript pour enlever un élément d'un tableau ?",
                Arrays.asList(
                        "remove()",
                        "pop()",
                        "delete()",
                        "shift()"
                ), 1);
        newQuestionList.add(question55);

        Question question56 = new Question("Quel est le nom du test qui vérifie que l'application est sécurisée contre les attaques ?",
                Arrays.asList(
                        "Test de compatibilité",
                        "Test de localisation",
                        "Test de sécurité",
                        "Test de charge"
                ), 2);
        newQuestionList.add(question56);

        Question question57 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour la création de scripts ?",
                Arrays.asList(
                        "Python",
                        "Java",
                        "Ruby",
                        "PHP"
                ), 2);
        newQuestionList.add(question57);

        Question question58 = new Question("Quel est le nom du framework JavaScript pour la création de sites Web interactifs ?",
                Arrays.asList(
                        "D3.js",
                        "jQuery",
                        "React",
                        "AngularJS"
                ), 1);
        newQuestionList.add(question58);

        Question question60 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour la création de jeux vidéo ?",
                Arrays.asList(
                        "Python",
                        "Java",
                        "C++",
                        "Ruby"
                ), 2);
        newQuestionList.add(question60);

        Question question61 = new Question("Quel est le nom du framework PHP pour la création de blogs ?",
                Arrays.asList(
                        "Laravel",
                        "Symfony",
                        "CodeIgniter",
                        "WordPress"
                ), 3);
        newQuestionList.add(question61);

        Question question62 = new Question("Quel est le nom de la méthode JavaScript pour ajouter un élément à un tableau ?",
                Arrays.asList(
                        "add()",
                        "push()",
                        "insert()",
                        "append()"
                ), 1);
        newQuestionList.add(question62);

        Question question63 = new Question("Quel est le nom du test qui vérifie que l'application fonctionne correctement sur différents navigateurs ?",
                Arrays.asList(
                        "Test de compatibilité",
                        "Test de localisation",
                        "Test de sécurité",
                        "Test de charge"
                ), 0);
        newQuestionList.add(question63);

        Question question64 = new Question("Quel est le nom du framework JavaScript pour la création de jeux vidéo ?",
                Arrays.asList(
                        "Phaser",
                        "Three.js",
                        "Babylon.js",
                        "Pixi.js"
                ), 0);
        newQuestionList.add(question64);

        Question question65 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour la création de sites Web dynamiques ?",
                Arrays.asList(
                        "Python",
                        "Java",
                        "PHP",
                        "Ruby"
                ), 2);
        newQuestionList.add(question65);

        Question question66 = new Question("Quel est le nom du framework PHP pour la création de réseaux sociaux ?",
                Arrays.asList(
                        "Laravel",
                        "Symfony",
                        "CodeIgniter",
                        "SocialEngine"
                ), 3);
        newQuestionList.add(question66);

        Question question67 = new Question("Quel est le nom de la méthode Python pour ajouter un élément à une liste ?",
                Arrays.asList(
                        "add()",
                        "append()",
                        "insert()",
                        "push()"
                ), 1);
        newQuestionList.add(question67);

        Question question68 = new Question("Quel est le nom du test qui vérifie que l'application est facile à utiliser pour les utilisateurs finaux ?",
                Arrays.asList(
                        "Test de compatibilité",
                        "Test de convivialité",
                        "Test de sécurité",
                        "Test de charge"
                ), 1);
        newQuestionList.add(question68);

        Question question69 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour la création de chatbots ?",
                Arrays.asList(
                        "Python",
                        "Java",
                        "Ruby",
                        "PHP"
                ), 0);
        newQuestionList.add(question69);

        Question question70 = new Question("Quel est le nom du framework JavaScript pour la création de sites Web réactifs ?",
                Arrays.asList(
                        "React",
                        "AngularJS",
                        "Ember.js",
                        "Vue.js"
                ), 0);
        newQuestionList.add(question70);

        Question question71 = new Question("Quel est le nom de la méthode Python pour concaténer deux chaînes de caractères ?",
                Arrays.asList(
                        "join()",
                        "concat()",
                        "merge()",
                        "append()"
                ), 0);
        newQuestionList.add(question71);

        Question question72 = new Question("Quel est le nom du framework PHP pour la création de sites Web eCommerce ?",
                Arrays.asList(
                        "Laravel",
                        "Symfony",
                        "CodeIgniter",
                        "WooCommerce"
                ), 3);
        newQuestionList.add(question72);

        Question question73 = new Question("Quel est le nom de la méthode JavaScript pour supprimer le dernier élément d'un tableau ?",
                Arrays.asList(
                        "remove()",
                        "delete()",
                        "pop()",
                        "shift()"
                ), 2);
        newQuestionList.add(question73);

        Question question74 = new Question("Quel est le nom du test qui vérifie que l'application peut gérer une grande quantité de données ?",
                Arrays.asList(
                        "Test de compatibilité",
                        "Test de charge",
                        "Test de sécurité",
                        "Test de localisation"
                ), 1);
        newQuestionList.add(question74);

        Question question75 = new Question("Quel est le nom du framework JavaScript pour la création de graphiques interactifs ?",
                Arrays.asList(
                        "Highcharts",
                        "Chart.js",
                        "D3.js",
                        "Plotly.js"
                ), 1);
        newQuestionList.add(question75);

        Question question76 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour la création d'applications de bureau ?",
                Arrays.asList(
                        "Python",
                        "Java",
                        "C++",
                        "Ruby"
                ), 2);
        newQuestionList.add(question76);

        Question question77 = new Question("Quel est le nom du framework PHP pour la création de forums en ligne ?",
                Arrays.asList(
                        "Laravel",
                        "Symfony",
                        "CodeIgniter",
                        "phpBB"
                ), 3);
        newQuestionList.add(question77);

        Question question78 = new Question("Quel est le nom de la méthode Python pour inverser l'ordre des éléments dans une liste ?",
                Arrays.asList(
                        "reverse()",
                        "invert()",
                        "sort()",
                        "shuffle()"
                ), 0);
        newQuestionList.add(question78);

        Question question79 = new Question("Quel est le nom du test qui vérifie que l'application peut être utilisée par plusieurs utilisateurs en même temps ?",
                Arrays.asList(
                        "Test de compatibilité",
                        "Test de charge",
                        "Test de sécurité",
                        "Test de localisation"
                ), 1);
        newQuestionList.add(question79);

        Question question80 = new Question("Quel est le nom du langage de programmation qui est souvent utilisé pour la création d'applications mobiles ?",
                Arrays.asList(
                        "Python",
                        "Java",
                        "Swift",
                        "Ruby"
                ), 1);
        newQuestionList.add(question80);

        Question question81 = new Question("Quel est le nom du framework JavaScript pour la création d'applications mobiles ?",
                Arrays.asList(
                        "React",
                        "React Native",
                        "Ionic",
                        "PhoneGap"
                ), 1);
        newQuestionList.add(question81);

        Question question82 = new Question("Qu'est-ce que PHP signifie?",
                Arrays.asList(
                        "Personal Homepage Processor",
                        "Hypertext Preprocessor",
                        "Private Hypertext Processor",
                        "Public Hypertext Processor"
                ), 1);
        newQuestionList.add(question82);

        Question question83 = new Question("Quel est l'avantage de l'utilisation des fonctions en JavaScript?",
                Arrays.asList(
                        "Elles permettent de réduire la quantité de code à écrire",
                        "Elles augmentent les performances de l'application",
                        "Elles rendent le code plus facile à lire",
                        "Elles sont obligatoires pour écrire du code en JavaScript"
                ), 0);
        newQuestionList.add(question83);

        Question question84 = new Question("Quel est le principal avantage de l'utilisation d'un framework en développement web?",
                Arrays.asList(
                        "Il permet de gagner du temps en fournissant des outils et des fonctionnalités prêtes à l'emploi",
                        "Il permet d'améliorer la sécurité du code",
                        "Il facilite la maintenance du code",
                        "Il rend le code plus léger et plus rapide"
                ), 0);
        newQuestionList.add(question84);

        Question question85 = new Question("Qu'est-ce que MySQL?",
                Arrays.asList(
                        "Un système de gestion de base de données relationnelles",
                        "Un outil de développement web en JavaScript",
                        "Un langage de programmation orienté objet",
                        "Un framework pour PHP"
                ), 0);
        newQuestionList.add(question85);

        Question question86 = new Question("Quelle est la fonctionnalité principale de Git?",
                Arrays.asList(
                        "La gestion de versionnement de code",
                        "La compilation de code",
                        "La génération de documentation de code",
                        "L'analyse de code"
                ), 0);
        newQuestionList.add(question86);

        Question question87 = new Question("Quelle est la fonction principale de Python?",
                Arrays.asList(
                        "L'analyse de données et l'apprentissage automatique",
                        "Le développement d'applications mobiles",
                        "La création de sites web dynamiques",
                        "La création de jeux vidéo"
                ), 0);
        newQuestionList.add(question87);

        Question question88 = new Question("Qu'est-ce que CSS signifie?",
                Arrays.asList(
                        "Cascading Style Sheets",
                        "Creative Style Sheets",
                        "Coding Style Sheets",
                        "Colorful Style Sheets"
                ), 0);
        newQuestionList.add(question88);

        Question question89 = new Question("Quel est le format de données le plus couramment utilisé pour échanger des données entre un serveur et une application web?",
                Arrays.asList(
                        "JSON",
                        "XML",
                        "CSV",
                        "TXT"
                ), 0);
        newQuestionList.add(question89);

        Question question90 = new Question("Quelle est la méthode la plus couramment utilisée pour tester du code?",
                Arrays.asList(
                        "Le test unitaire",
                        "Le test de performance",
                        "Le test de sécurité",
                        "Le test d'intégration"
                ), 0);
        newQuestionList.add(question90);


        Question question91 = new Question("Qu'est-ce que JavaScript?",
                Arrays.asList(
                        "Un navigateur web",
                        "Un langage de programmation",
                        "Un système d'exploitation",
                        "Un réseau social"
                ), 1);
        newQuestionList.add(question91);

        Question question92 = new Question("Quel est l'acronyme de PHP?",
                Arrays.asList(
                        "Personal Home Page",
                        "PHP: Hypertext Preprocessor",
                        "Public Hosting Platform",
                        "Programming Home Page"
                ), 1);
        newQuestionList.add(question92);

        Question question93 = new Question("Quelle est la différence entre Python 2 et Python 3?",
                Arrays.asList(
                        "Python 2 est plus récent que Python 3",
                        "Python 2 utilise une syntaxe différente de Python 3",
                        "Python 2 est obsolète et n'est plus maintenu",
                        "Python 2 est plus rapide que Python 3"
                ), 2);
        newQuestionList.add(question93);

        Question question94 = new Question("Qu'est-ce qu'une base de données?",
                Arrays.asList(
                        "Un ensemble de fichiers",
                        "Un système de stockage de données",
                        "Un langage de programmation",
                        "Une application mobile"
                ), 2);
        newQuestionList.add(question94);

        Question question95 = new Question("Quel est le framework PHP le plus utilisé?",
                Arrays.asList(
                        "Laravel",
                        "Symfony",
                        "CodeIgniter",
                        "Zend"
                ), 1);
        newQuestionList.add(question95);

        Question question96 = new Question("Qu'est-ce que le responsive design?",
                Arrays.asList(
                        "Un type de langage de programmation",
                        "Une méthode pour concevoir des sites web adaptatifs",
                        "Un framework pour le développement web",
                        "Une technique de sécurité pour les applications web"
                ), 2);
        newQuestionList.add(question96);

        Question question97 = new Question("Quel est le rôle de CSS dans le développement web?",
                Arrays.asList(
                        "Gérer le contenu de la page web",
                        "Styler et mettre en forme la page web",
                        "Interagir avec la base de données",
                        "Générer du code HTML"
                ), 2);
        newQuestionList.add(question97);

        Question question98 = new Question("Qu'est-ce que Git?",
                Arrays.asList(
                        "Un système de gestion de base de données",
                        "Un framework pour le développement web",
                        "Un logiciel de création de graphiques",
                        "Un système de gestion de version de code source"
                ), 4);
        newQuestionList.add(question98);

        Question question99 = new Question("Quel est le rôle de Django dans le développement web?",
                Arrays.asList(
                        "Gérer le contenu de la page web",
                        "Styler et mettre en forme la page web",
                        "Interagir avec la base de données",
                        "Générer du code HTML"
                ), 3);
        newQuestionList.add(question99);

        Question question100 = new Question("Qu'est-ce que le TDD?",
                Arrays.asList(
                        "Test Driven Development",
                        "Très Dangereux Développement",
                        "Team Development Development",
                        "Technology Driven Design"
                ), 1);
        newQuestionList.add(question100);

        Question question101 = new Question("Qu'est-ce que SQL?",
                Arrays.asList(
                        "Structured Query Language",
                        "Software Quality Level",
                        "Software Query Language",
                        "Structured Quality Level"
                ), 1);
        newQuestionList.add(question101);

        Question question102 = new Question("Qu'est-ce que Node.js?",
                Arrays.asList(
                        "Un navigateur web",
                        "Un framework PHP",
                        "Un environnement d'exécution JavaScript côté serveur",
                        "Un système d'exploitation"
                ), 3);
        newQuestionList.add(question102);


        return new QuestionBank(newQuestionList);
    };
}