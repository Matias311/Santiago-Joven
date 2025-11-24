import React, { useEffect, useRef, useState, type JSX } from "react";
import OpenAI from "openai";
import "./chatbot.css";

type Message = {
  sender: "bot" | "user";
  text: string;
};

const client = new OpenAI({
  apiKey: import.meta.env.VITE_OPENAI_API_KEY,
  dangerouslyAllowBrowser: true,
});

export default function Chatbot(): JSX.Element {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [input, setInput] = useState<string>("");
  const [messages, setMessages] = useState<Message[]>([
    { sender: "bot", text: "Hola soy tu asistente personal." },
  ]);
  const chatRef = useRef<HTMLDivElement | null>(null);
  const inputRef = useRef<HTMLInputElement | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [text, setText] = useState("");

  useEffect(() => {
    fetch("/data/chatbotText.txt")
      .then((res) => res.text())
      .then((data) => setText(data));
  }, []);

  useEffect(() => {
    // Auto-scroll al final cuando cambian los mensajes
    if (chatRef.current) {
      chatRef.current.scrollTop = chatRef.current.scrollHeight;
    }
  }, [messages]);

  useEffect(() => {
    // Enfocar input cuando se abre
    if (isOpen) {
      setTimeout(() => inputRef.current?.focus(), 120);
    }
  }, [isOpen]);

  const sendMessage = async (e?: React.FormEvent) => {
    if (e) e.preventDefault();
    const trimmed = input.trim();
    if (!trimmed) return;

    const userMsg: Message = { sender: "user", text: trimmed };
    setMessages((prev) => [...prev, userMsg]);
    setInput("");
    setLoading(true);

    try {
      const response = await client.chat.completions.create({
        model: "gpt-4o-mini",
        messages: [
          { role: "system", content: text },
          { role: "user", content: trimmed },
        ],
      });

      const botReply =
        response.choices?.[0]?.message?.content ?? "No pude responder 😢";

      setMessages((prev) => [...prev, { sender: "bot", text: botReply }]);
    } catch (err) {
      console.error("OpenAI error:", err);
      setMessages((prev) => [
        ...prev,
        { sender: "bot", text: "Hubo un error al procesar tu solicitud." },
      ]);
    } finally {
      setLoading(false);
    }
  };

  const toggleOpen = () => setIsOpen((s) => !s);

  return (
    <>
      {/* Botón flotante en esquina inferior izquierda */}
      <div
        className={`section-chat ${isOpen ? "hidden-button" : ""} floating-widget`}
      >
        <button
          className="chat-button"
          onClick={toggleOpen}
          aria-label="Abrir chat"
          title="Abrir chat"
        >
          <img src="/icon-chatbot.png" alt="chatbot" />
        </button>
      </div>

      {/* Popup del chatbot */}
      <div
        className={`popup ${isOpen ? "show" : "hide"}`}
        role="dialog"
        aria-modal="true"
      >
        <div className="header-chatbot">
          <h3>Asistente Virtual</h3>
          <button
            className="close-btn"
            onClick={() => setIsOpen(false)}
            aria-label="Cerrar chat"
            title="Cerrar"
          >
            ✕
          </button>
        </div>

        <hr />

        <div ref={chatRef} className="historial-chatbot" aria-live="polite">
          {messages.map((msg, i) => (
            <p
              key={i}
              className={
                msg.sender === "bot" ? "chatBubbleBot" : "chatBubblePersona"
              }
            >
              {msg.text}
            </p>
          ))}

          {loading && <p className="chatBubbleBot">Escribiendo...</p>}
        </div>

        <form className="chat-form" onSubmit={sendMessage}>
          <input
            ref={inputRef}
            type="text"
            id="input-chat"
            placeholder="Escriba su pregunta..."
            value={input}
            onChange={(e) => setInput(e.target.value)}
            autoComplete="off"
            aria-label="Escriba su pregunta"
            required
          />
          <button type="submit" aria-label="Enviar mensaje" title="Enviar">
            <span className="send-icon">➤</span>
          </button>
        </form>
      </div>
    </>
  );
}
