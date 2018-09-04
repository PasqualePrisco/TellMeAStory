//webkitURL is deprecated but nevertheless
URL = window.URL || window.webkitURL;

var gumStream; 						//stream from getUserMedia()
var rec; 							//Recorder.js object
var input; 							//MediaStreamAudioSourceNode we'll be recording

// shim for AudioContext when it's not avb. 
var AudioContext = window.AudioContext || window.webkitAudioContext;
var audioContext //audio context to help us record

var recordButton = document.getElementById("recordButton");
var stopButton = document.getElementById("stopButton");
var listenButton= document.getElementById("listenButton");
var saveButton = document.getElementById("saveButton");
var deleteButton = document.getElementById("deleteButton");
var closeButton = document.getElementById("closeAudio");

var child;
var value;
var question;

//add events to those 2 buttons
recordButton.addEventListener("click", startRecording);
stopButton.addEventListener("click", stopRecording);
deleteButton.addEventListener("click", deleteAudio);
closeButton.addEventListener("click", close);


function audioAdd(ob){
	 listenButton.disabled=true;
	 deleteButton.disabled=true;
	question= document.getElementById("question");
	if(question!=null){
	question.remove();
	}
	// ####### Da rivedere
	if(recordingsList.firstChild!=null){
	 recordingsList.firstChild.remove();
	}
	 value = ob.value;
	 child= ob.childNodes[0];
	 console.log(value);
 if(value != "null"){
	 console.log("ci entro");
	 var au = document.createElement("audio");
	 au.controls = true;
	 au.controlsList="nodownload"
	 au.src = value;
	 au.id="player";
	 au.classList.add("hidden");
	 recordingsList.appendChild(au);
	 listenButton.disabled = false;
	 deleteButton.disabled = false;
 }
}


function startRecording() {
	console.log("recordButton clicked");

	/*
		Simple constraints object, for more advanced audio features see
		https://addpipe.com/blog/audio-constraints-getusermedia/
	*/
    
    var constraints = { audio: true, video:false }

 	/*
    	Disable the record button until we get a success or fail from getUserMedia() 
	*/
    audioContext = new AudioContext();
    
	recordButton.disabled = true;
	stopButton.disabled = false;

	/*
    	We're using the standard promise based getUserMedia() 
    	https://developer.mozilla.org/en-US/docs/Web/API/MediaDevices/getUserMedia
	*/

	navigator.mediaDevices.getUserMedia(constraints).then(function(stream) {
		console.log("getUserMedia() success, stream created, initializing Recorder.js ...");

		/*
			create an audio context after getUserMedia is called
			sampleRate might change after getUserMedia is called, like it does on macOS when recording through AirPods
			the sampleRate defaults to the one set in your OS for your playback device

		*/

		//update the format 
	//	document.getElementById("formats").innerHTML="Format: 1 channel pcm @ "+audioContext.sampleRate/1000+"kHz"

		/*  assign to gumStream for later use  */
		gumStream = stream;
		
		/* use the stream */
		input = audioContext.createMediaStreamSource(stream);

		/* 
			Create the Recorder object and configure to record mono sound (1 channel)
			Recording 2 channels  will double the file size
		*/
		rec = new Recorder(input,{numChannels:1})

		//start the recording process
		rec.record()

		console.log("Recording started");

	}).catch(function(err) {
	  	//enable the record button if getUserMedia() fails
    	recordButton.disabled = false;
    	stopButton.disabled = true;
	});
}


function stopRecording() {
	console.log("stopButton clicked");

	//disable the stop button, enable the record too allow for new recordings
	stopButton.disabled = true;
	recordButton.disabled = false;

	
	//tell the recorder to stop the recording
	rec.stop();

	//stop microphone access
	gumStream.getAudioTracks()[0].stop();

	//create the wav blob and pass it on to createDownloadLink
	rec.exportWAV(createDownloadLink);
}


function deleteAudio() {

	var r=new XMLHttpRequest();
	  r.onload=function(e) {
	      if(this.readyState === 4) {
	          window.location.assign("seeVignette.jsp");
	      }
	  };
	  r.open("POST","addAlbum?action=removeAudio&id="+child.value,true);
	  r.send(null);
	  
}


function close() {
	 console.log("ci entro nel close");
	//tell the recorder to stop the recording
	 if(rec!=null){
		 rec.stop();
		 //stop microphone access
		 gumStream.getAudioTracks()[0].stop();
		 recordButton.disabled = false;
			stopButton.disabled = true;
	 }
	 var sound = document.getElementById("player");
	 if(player!=null){
	 sound.pause();
	 sound.currentTime = 0;
	 }
}

function createDownloadLink(blob) {
	
	var url = URL.createObjectURL(blob);
	var au = document.createElement('audio');
	var link = document.createElement('a');

	//name of .wav file to use during upload and download (without extendion)
	var filename = new Date().toISOString();

	//add controls to the <audio> element
	au.controls = true;
	au.controlsList="nodownload"
	au.src = url;
	au.id="player";
	au.classList.add("hidden");
	

	//save to disk link
	link.href = url;
	link.download = filename+".wav"; //download forces the browser to donwload the file using the  filename
	link.innerHTML = "Save to disk";
	
	
	//add the new audio element to div
	if (recordingsList.firstChild) {
	    // It has at least one
		recordingsList.firstChild.remove();
	}
	recordingsList.appendChild(au);
	listenButton.disabled = false;
	saveButton.disabled = false;
	/**
	//add the filename to the li
	li.appendChild(document.createTextNode(filename+".wav "))

	//add the save to disk link to li
	li.appendChild(link); */
	
	//upload link
	saveButton.addEventListener("click", function(event){
		question = document.getElementById("question");
		if(value != "null" && question==null){
			var audioBody = document.getElementById("audio-body");
			var p = document.createElement('p');
			p.style.color="red";
			p.style.textAlign="center";
			p.style.marginTop="10px";
			p.innerHTML= "Esiste gi&agrave un audio per questa vignetta se vuoi sovrascriverlo clicca di nuovo su salva";
			p.id="question"
			audioBody.append(p);
		}else{
		  var xhr=new XMLHttpRequest();
		  xhr.onload=function(e) {
		      if(this.readyState === 4) {
		          window.location.assign("seeVignette.jsp");
		      }
		  };
		  var fd=new FormData();
		  fd.append("audio_data",blob, filename);
		  xhr.open("POST","addAlbum?action=addAudio&id="+child.value,true);
		  xhr.send(fd);
		}
	})
	//add a space in between
	//add the upload link to li

	//add the li element to the ol

}