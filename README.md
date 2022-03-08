# RIMEL
code base for research on rimel subject

__Authors__:
  * [ANAGONOU Patrick]()
  * [ANIGLO Jonas]()
  * [FRANCIS Anas]()
  * [ZABOURDINE Soulaiman]()
 
 ## Analyser
 It is a python module that helps to download main.tf from github's project which matches given conditions. it can be launch by running
 For example to download all projects which matches the language html and with terraform topic
 To run it move to the analyser folder with
 
    $ cd analyser
    
and run the script with

    $ python3 .\main.py terraform html
 
 ### Warning
  It is possible that you get error Null error from the scrypt, if that happens you must change the token that might be expired. Use this [link](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) to generate your own, after that replace the value of the token variable in analyser/github_api_research.py by the one you generated.Also,The download of projects can be long . It is due to the limit of request that the github api setted, but don't worry the script waits and retries every 5s, that is why the download can take be long  sometimes.
