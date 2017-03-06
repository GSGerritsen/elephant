var dummyMessages = [];

const config = {
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
    }
};

new Vue({
    el: '#root',

    data: {
        messages: dummyMessages,
        sent: dummyMessages,
        searchedMessages: dummyMessages,
        currentMessage: 0,
        currentMessageBody: '',
        showReplyModal: false,
        showComposeModal: false,
        showUserModal: false,
        currentUserEmail: '',
        timer: '',
        currentMessageSet: 'inbox',
        numberUnread: 0,
        keyword: "",
        sendTo: ''
    },

    created: function() {
        var self = this;
        axios.get('/home').then(function(response) {
            if (response.status === 200) {
                self.currentUserEmail = response.data;
                axios.get('/messages', {
                    params: {
                        requester: self.currentUserEmail,
                        wants: self.currentMessageSet,
                        keyword: self.keyword === "" ? "ignore_search" : self.keyword
                    }
                }).then(function(response) {
                    console.log(response);
                    if (response.status == 200) {
                        self.messages = response.data;
                        for (var i = 0; i < self.messages.length; i++) {
                            if (self.messages[i].opened === false) {
                                self.numberUnread++;
                            }
                        }
                    }
                });
            }
        });
        this.timer = setInterval(this.getMessages, 30000);
    },

    beforeDestroy: function() {
      clearInterval(this.timer);
    },


    methods: {
        getMessages: function() {
            var self = this;
            axios.get('/messages', {
                params: {
                    requester: self.currentUserEmail,
                    wants: self.currentMessageSet,
                    keyword: self.keyword === "" ? "ignore_search" : self.keyword
                }
            }).then(function(response) {
                console.log(response);
                if (response.status == 200) {
                    if (self.currentMessageSet === 'inbox') {
                        self.messages = response.data;
                        for (var i = 0; i < self.messages.length; i++) {
                            if (self.messages[i].opened === false) {
                                self.numberUnread++;
                            }
                        }
                    }
                    if (self.currentMessageSet === 'sent') {
                        self.sent = response.data;
                    }

                    if (self.currentMessageSet === 'searchedMessages') {
                        // check for no search results
                        if (response.data.length === 0) {
                            return;
                        }
                        self.searchedMessages = response.data;
                    }
                }
            });
        },
        getSent: function() {
          this.currentMessage = 0;
          this.currentMessageSet = 'sent';
          this.getMessages();
        },
        getInbox: function() {
            this.currentMessage = 0;
            this.currentMessageSet = 'inbox';
            this.getMessages();
        },

        getSearchMessages: function() {
            this.searchedMessages = [];
            this.currentMessage = 0;
            this.currentMessageSet = 'searchedMessages';
            this.getMessages();
        },

        clickOnMessage: function(event) {
            // This feels kind of hacky but not sure about a better way to do it currently
            var self = this;
            var message = event.currentTarget;
            var re = /index">(\d+)/;
            var reMatch = message.innerHTML.match(re);
            var index = reMatch[1];
            this.currentMessage = parseInt(index);
            // Here we'll do something like axios.post(markAsRead) {}
            if (this.currentMessageSet === 'inbox') {
                this.messages[index].opened = true;
                axios.post('/update_message', {
                    messageDetailsId: this.messages[this.currentMessage].messageId,
                    email: this.currentUserEmail
                }).then(function(response) {
                    console.log(response);
                })
            }
        },

        sendMessageReply: function() {
            var self = this;

            axios.post('/messages', {
                message: this.currentMessageBody,
                to: this.messages[this.currentMessage].fromEmail,
                from: this.currentUserEmail
            }, config).then(function(response) {
                console.log(response)
            });
            this.showReplyModal = false;
            this.currentMessageBody = '';
        },

        sendComposition: function() {
            console.log(this.sendTo);
            axios.post('/messages', {
                message: this.currentMessageBody,
                to: this.sendTo,
                from: this.currentUserEmail
            }, config).then(function(response) {
                console.log(response)
            });
            this.showComposeModal = false;
            this.currentMessageBody = '';
            this.sendTo = '';
        },

        closeModal: function() {
           $emit('close');
        }
    }
});



Vue.component('compose', {
    template: '#compose-template'
});

Vue.component('compose-general', {
    template: '#compose-general-template'
});







