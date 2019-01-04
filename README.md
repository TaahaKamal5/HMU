Hit Me Up Prototype Documentation
HMU or (hit me up)is an Android App created by a group of UT Dallas students to tackle the buy/sell/borrow/trade goods problem at UTD.

Project Dependencies:
	
	com.android.support:appcompat-v7:27.1.1
	com.android.support:cardview-v7:27.1.1
	com.android.support:recyclerview-v7:27.1.1
	com.android.support.constraint:constraint-layout:1.1.3
	com.android.support:design:27.1.1
	com.android.support:support-v4:27.1.1
	junit:junit:4.12
	com.android.support.test:runner:1.0.2
	com.android.support.test.espresso:espresso-core:3.0.2
	
	com.google.firebase:firebase-core:16.0.1
		Dependancy for firestore to work all it is used for.
		
	com.google.firebase:firebase-firestore:16.0.0
		Most of the item tracking and controls work with their firestore data.
		
	com.google.firebase:firebase-auth:16.0.1
		In HMU the sign in and up process relies heavily on this library. This keeps user ids 
	login emails, password recovery, etc.
	
	com.android.support:multidex:1.0.3
		The abundance of dependancies in the project currently calls for multidex support. This
	opens up enough memory to use all our dependencies.
	

Java Class Explanations:

*************************************************************************************************************
Add Item Class

		The Item class implements the Parceable class so that it can be passed through intents
	The Item class's constructor has 8 parameters which are a string called name, a string called
	description, a string called picture, a string called sellerNETID, a string called condition, a 
	boolean called status, and a double called price. All of these arguments are assigned to private
	fields of the corresponding datatype. The item class has getters and setters for every field that was
	assigned in the constructor, describeContents, a writeToParcel which has a Parcel parameter named dest 
	and an int paremeter called flags.
*************************************************************************************************************
