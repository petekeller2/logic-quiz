package peter.keller.logicquiz;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String question;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private int currentQuestionNumber;
	private int correct = 0;
	private int numberOfQuestionsAnswered = 1;
		
	//the string arrays below should all contain the same number of strings
	String[] questionsArray = new String[]{
			"All dogs are animals. Some animals are carnivores. Some carnivores are animals. Based on the above: ",
			"No vampires are zombies and all walking dead are zombies.  No walking dead are shape-shifters. Based on the above: ",
			"All cats are furry.  Some cats are wild.  Some furry things are not striped. Based on the above: ",
			"In spite of the best efforts of the scientific establishment, no cure for cancer has been forthcoming. Therefore, I " +
			"conclude that it is pointless to continue spending large sums of money looking for a cure.In spite of the best efforts of the" +
			" scientific establishment, no cure for cancer has been forthcoming. Therefore, I conclude that it is pointless to continue" +
			" spending large sums of money looking for a cure. The falacy is: ",
			"Mr. Smith has set forth her reasons for the comparatively low salaries for postal employees. Her arguments are hardly deserving" +
			" of careful consideration. After all, she has worked for the US Post Office for the last forty years. The falacy is: ",
			" If you ask around, you will see that most people think that algebra is pretty useless in later life. For this reason, I cannot" +
			" understand why we have to take a university course in this subject. The falacy is: ",
			"Michael Greensby, one of the most erudite writers of the latter twentieth century, has recently endorsed the use of herbal" +
			" remedies for rheumatism. Surely this conclusion of such a deep thinker is one that we all should  seriously consider. The falacy is: ",
			"John R. Smithey, leader of the Western Christian Leadership Society, a brilliant lecturer and commentator, has endorsed the" +
			" Instant Bran-Systematics Weight Loss Program.  Hence, I know that this is a good program because the opinion of a person of " +
			"that caliber cannot be lightly dismissed. The falacy is: ",
			" There is absolutely no convincing evidence which has been put forward to indicate that Existentialism as taught at Lander " +
			"University is not the very best class offered there. Consequently, I conclude that there is no better course taught there. The falacy is: ",
			" Look, Mr. Jackson, I have listened very patiently to your arguments and reasons for your appeal to the City of Greenwood to pave a " +
			"sidewalk on Woodland Way. I think the real reason you want it is to enhance the value of your house there. Because you are such a" +
			" self-seeker, we are going to deny your request. The falacy is: "};
	String[] answerArray1 = new String[]{
			"All dogs are carnivores",
			"All shape-shifters are zombies",
			"Some cats are striped",
			"ad ignorantiam",
			"ad ignorantiam",
			"ad ignorantiam",
			"ad ignorantiam",
			"ad ignorantiam",
			"ad ignorantiam",
			"ad ignorantiam"};
	String[] answerArray2 = new String[]{
			"All carnivores are animals",
			"No vampires are walking dead",
			"Some cats are not wild",
			"ad verecundiam",
			"ad verecundiam",
			"ad verecundiam",
			"ad verecundiam",
			"ad verecundiam",
			"ad verecundiam",
			"ad verecundiam"};
	String[] answerArray3 = new String[]{
			"Some dogs are carnivores",
			"All shape-shifters are vampires",
			"Some cats are not striped",
			"ad hominem",
			"ad hominem",
			"ad hominem",
			"ad hominem",
			"ad hominem",
			"ad hominem",
			"ad hominem"};
	String[] answerArray4 = new String[]{
			"Some animals are not carnivores",
			"All vampires are zombies",
			"Some furry things are wild",
			"ad populum",
			"ad populum",
			"ad populum",
			"ad populum",
			"ad populum",
			"ad populum",
			"ad populum"};
	int[] answerKeyArray = new int[]{//1-4
			4,
			2,
			2,
			1,
			3,
			4,
			2,
			2,
			1,
			3};
	
	TextView t;
	Button btn1;
	Button btn2;
	Button btn3;
	Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        t = new TextView(this);
        t = (TextView)findViewById(R.id.text1); 
        btn1 = new Button(this);
        btn1 = (Button)findViewById(R.id.imageButton1);
        btn2 = new Button(this);
        btn2 = (Button)findViewById(R.id.imageButton2);
        btn3 = new Button(this);
        btn3 = (Button)findViewById(R.id.imageButton3);
        btn4 = new Button(this);
        btn4 = (Button)findViewById(R.id.imageButton4);
        

        buttonClick1();
        buttonClick2();
        buttonClick3();
        buttonClick4();
        
        //to get a random first question
        answered(0);
        setText();

    }
    
    public void setText()
    {
        SharedPreferences.Editor editor = getSharedPreferences("peter.keller.logicquiz", MODE_PRIVATE).edit();

        editor.putString("name", getQuestion());
        editor.putString("answer1", getAnswer1());
        editor.putString("answer2", getAnswer2());
        editor.putString("answer3", getAnswer3());
        editor.putString("answer4", getAnswer4());
        editor.commit();
        
        SharedPreferences prefs = getSharedPreferences("peter.keller.logicquiz", MODE_PRIVATE); 

                
        t.setText(prefs.getString("name", "empty"));
        btn1.setText(prefs.getString("answer1", "empty"));
        btn2.setText(prefs.getString("answer2", "empty"));
        btn3.setText(prefs.getString("answer3", "empty"));
        btn4.setText(prefs.getString("answer4", "empty"));
    }
    
    public void answered(int answer) {
		

    	if(numberOfQuestionsAnswered==5)
    	{
    		//display end game message
    		
        	if(answer==answerKeyArray[currentQuestionNumber] && answer!=0)
        	{
        		//right
        		correct++;
        	}
 
        	//numberOfQuestionsAnswered++;

    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setTitle("Your Score is:");
    		builder.setMessage(correct + "/" + numberOfQuestionsAnswered);
    		builder.setPositiveButton("Play again", new DialogInterface.OnClickListener() {
    			  public void onClick(DialogInterface dialogInterface, int id) {
    				  correct=0;
    		    	  numberOfQuestionsAnswered=1;
    		      	 // Log.d("Play again clicked", "play again clicked");
    		      	 // Log.d(String.valueOf(correct), "number of correct answers");
    		      	 // Log.d(String.valueOf(numberOfQuestionsAnswered), "number of questions answered");

    			  }
    		});
    		AlertDialog dialog = builder.show();

    		TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
    		messageView.setGravity(Gravity.CENTER);
    		
    		
    	}
    	//answer!=0 is checked because 0 is used to create the first question
    	if(answer==answerKeyArray[currentQuestionNumber] && answer!=0)
    	{
    		//right
        	numberOfQuestionsAnswered++;
    		correct++;
    	}

    	if(answer!=answerKeyArray[currentQuestionNumber] && answer!=0)
    	{
    		//wrong
        	numberOfQuestionsAnswered++;
    	}
    	
    	Random rnd = new Random();
    	int random = rnd.nextInt(questionsArray.length);
    	
    	currentQuestionNumber = random;
    	question = questionsArray[random];
    	answer1 = answerArray1[random];
    	answer2 = answerArray2[random];
    	answer3 = answerArray3[random];
    	answer4 = answerArray4[random];
    	//Log.d(String.valueOf(random), "random number");
    	//Log.d(String.valueOf(correct), "number of correct answers");
    	//Log.d(String.valueOf(numberOfQuestionsAnswered), "number of questions answered");

    }
    
    public String getQuestion() {
    	return question;
    }
    
    public String getAnswer1() {
    	return answer1;
    }
    
    public String getAnswer2() {
    	return answer2;
    }
    
    public String getAnswer3() {
    	return answer3;
    }
    
    public String getAnswer4() {
    	return answer4;
    }
    
	public void buttonClick1() {
				 
		btn1.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
			
		        answered(1);
		        setText();   
			}
			 
		});
	}
    

	public void buttonClick2() {
			 
		btn2.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
					
		        answered(2);
		        setText();
			}
			 
		});
	}
	
	public void buttonClick3() {
				 
		btn3.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
			
		        answered(3);
		        setText();
			}
			 
		});
	}
    
	public void buttonClick4() {
				 
		btn4.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
		        answered(4);
		        setText();
			}
			 
		});
	}
	
    
}
