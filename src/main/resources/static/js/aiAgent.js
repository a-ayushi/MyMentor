
window.addEventListener("DOMContentLoaded", function() {
          window.AgentInitializer.init({
            agentRenderURL: "https://agent.jotform.com/01967d36677575e39022c9b84455fbeab336",
            rootId: "JotformAgent-01967d36677575e39022c9b84455fbeab336",
            formID: "01967d36677575e39022c9b84455fbeab336",
            queryParams: ["skipWelcome=1", "maximizable=1"],
            domain: "https://www.jotform.com",
            isDraggable: false,
            background: "linear-gradient(180deg, #6C73A8 0%, #6C73A8 100%)",
            buttonBackgroundColor: "#0066C3",
            buttonIconColor: "#FFFFFF",
            variant: false,
            customizations: {
              "greeting": "Yes",
              "greetingMessage": "Hi! How can I assist you?",
              "openByDefault": "No",
              "pulse": "Yes",
              "position": "right",
              "autoOpenChatIn": "0"
            },
            isVoice: false,
          });

        });