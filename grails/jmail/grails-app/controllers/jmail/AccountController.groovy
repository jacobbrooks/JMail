package jmail

import grails.converters.JSON
import java.util.Random

class AccountController {

    def signup() { 
    	def account = new Account(JSON.parse(request.JSON.toString()))
  		def token = generateToken(account.username)
  		account.token = token
  		account.save()

    	def res = "{\"token\":\"" + account.token + "\"}"

    	response.setContentType("application/JSON")
    	response << res
    }

    def login() {
    	def account = Account.findWhere(username: request.JSON.username, password: request.JSON.password)

    	def res = "{\"token\":\"" + account.token + "\"}"
    	
    	response.setContentType("application/JSON")
    	response << res
    }

    def delete() {
    	def account = Account.findWhere(username: request.JSON.username, password: request.JSON.password)
    	def deletable = Account.get(account.id)
    	deletable.delete(flush: true)

    	def res = "{\"response\":\"account deleted\"}"

    	response.setContentType("application/JSON")
    	response << res
    }

    def generateToken(username) {
    	def token = ""
    	for(int i = 0; i < username.length(); i++){
    		token += username.charAt(i)
    		token += username.charAt(Math.abs(new Random().nextInt() % username.length()))
    		token += Math.abs(new Random().nextInt() % username.length())
    	}
    	return token
    }
}
