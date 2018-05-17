package jmail

import grails.converters.JSON

class MessageController {

    def send() { 
    	def message = new Message(JSON.parse(request.JSON.toString()))
    	message.save()

    	def res = "{\"response\":\"message sent\"}"

    	response.setContentType("application/JSON")
    	response << res
    }

    def inbox() {

    	def token = request.getHeader("token")
    	def from = params.from

    	def recipient = Account.findWhere(token: token)
    	def username = recipient.username

    	def messages
    	if(from.equals("all")){
    		messages = Message.findAllWhere(recipient: username)
    	}else{
    		def sender = Account.findWhere(username: from)
    		def senderToken = sender.token
    		messages = Message.findAllWhere(token: senderToken, recipient: username)
    	}

    	def messageList = messages.asList()

    	JSON.registerObjectMarshaller(Message) {
    		def output = [:]
   			output['id'] = it.id
   			output['from'] = Account.findWhere(token: it.token).username
    		output['subject'] = it.subject
    		output['message'] = it.message
    		return output;
		}

		def res = messageList as JSON

    	response.setContentType("application/JSON")
    	response << res
    }

    def delete() {
    	def account = Account.findWhere(token: request.JSON.token)
    	def id = new Long(request.JSON.id)
    	def message = Message.findWhere(id: id, recipient: account.username)
    	def deletable = Message.get(message.id)
    	deletable.delete(flush: true)

    	def res = "{\"response\":\"message deleted\"}"

    	response.setContentType("application/JSON")
    	response << res
    }
}
