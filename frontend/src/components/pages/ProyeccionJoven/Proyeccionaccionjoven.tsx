import { useState, type ChangeEvent } from "react";
import "./Proyeccionaccionjoven.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faRocket,
  faBullhorn,
  faRobot,
  faPersonRunning,
  faHandshake,
  faPersonCane,
  faLocationDot,
  faClock,
  faEnvelope,
} from "@fortawesome/free-solid-svg-icons";

const byPrefixAndName = {
  fas: {
    "location-dot": faLocationDot,
    clock: faClock,
    envelope: faEnvelope,
  },
};

type Theme = "blue" | "orange";

type Cta = {
  label: string;
  action: string;
};

type Card1Data = {
  id: string;
  icon: React.ReactNode;
  title: string;
  tagline?: string | null;
  bullets: string[];
  cta: Cta;
};

type Card2Data = {
  id: string;
  icon: React.ReactNode;
  title: string;
  body: string;
  bodyIcon?: React.ReactNode | null;
  cta: Cta;
};

type CardData = Card1Data | Card2Data;

type Hero = {
  title: string;
  subtitle: string;
  tabs: { id: string; label: string; icon: React.ReactNode }[];
};

type ContactData = {
  title: string;
  subtitle: string;
  address: string;
  schedule: string[];
  email: string;
  mapEmbedUrl: string;
  photoUrl: string | null;
};

type FormState = {
  nombre: string;
  correo: string;
  mensaje: string;
};

type Card1Props = Card1Data & {
  onCta?: (action: string) => void;
};

type Card2Props = Card2Data & {
  onCta?: (action: string) => void;
};

type CardProps = { theme: Theme; onCta?: (action: string) => void } & CardData;

type CardSectionProps = {
  title: string;
  subtitle: string;
  theme: Theme;
  cards: CardData[];
  onCta?: (action: string) => void;
};

type ContactSectionProps = {
  data: ContactData;
};

type ProyeccionAccionJovenProps = {
  hero?: Hero;
  sections?: {
    id: string;
    title: string;
    subtitle: string;
    theme: Theme;
    cards: CardData[];
  }[];
  contact?: ContactData;
  onCta?: (action: string) => void;
};

// ─── MOCK ── reemplazar con fetch/props cuando exista la API ───────────────
const MOCK: {
  hero: Hero;
  sections: {
    id: string;
    title: string;
    subtitle: string;
    theme: Theme;
    cards: CardData[];
  }[];
  contact: ContactData;
} = {
  hero: {
    title: "Proyección y Acción Joven",
    subtitle:
      "¡Tu energía puede cambiar las cosas! Súmate a nuestras iniciativas sociales y proyectos de voluntariado.",
    tabs: [
      {
        id: "proyeccion",
        label: "Proyección",
        icon: <FontAwesomeIcon icon={faRocket} />,
      },
      {
        id: "accion",
        label: "Acción Joven",
        icon: <FontAwesomeIcon icon={faBullhorn} />,
      },
    ],
  },
  sections: [
    {
      id: "cursos",
      title: "Cursos Destacados",
      subtitle: "Impulsa tu futuro. Cursos y herramientas para tu crecimiento.",
      theme: "blue", // → determina Card1 (blue) o Card2 (orange)
      cards: [
        {
          id: "c1",
          icon: <FontAwesomeIcon icon={faRobot} />,
          title: "Introducción a la Inteligencia Artificial",
          tagline: "¿Te interesa el uso de la IA?",
          // 4 textos alrededor del ícono central (Card1)
          bullets: [
            "Descubre los fundamentos de la IA, aprende sobre machine learning y cómo esta tecnología está cambiando el mundo.",
            "Crea tu propia Inteligencia Artificial desde 0. Aplica lo aprendido y dale un avance tecnológico a tus proyectos.",
            "Aprenderás sobre las redes neuronales y su increíble capacidad de crear ideas o proyectos únicos.",
            "Obtendrás habilidades imprescindibles para el desarrollo tecnológico futuro, ¡no te quedes atrás!",
          ],
          cta: { label: "Inscríbete Aquí", action: "enroll" },
        },
      ],
    },
    {
      id: "accion",
      title: "Acción Joven",
      subtitle: "¡Tu energía puede cambiar las cosas! Súmate al voluntariado.",
      theme: "orange", // → Card2
      cards: [
        {
          id: "a1",
          icon: <FontAwesomeIcon icon={faBullhorn} />,
          title: "Proyectos de Impacto Social",
          body: "Participa en campañas de reforestación, visitas a hogares de ancianos, colectas de alimentos y muchas otras actividades pensadas para generar un impacto positivo en la comunidad. Comparte experiencias, aprende nuevas habilidades y conecta con otros jóvenes que buscan marcar la diferencia.",
          // ícono grande decorativo inferior (Card2)
          bodyIcon: (
            <>
              <FontAwesomeIcon icon={faPersonRunning} />{" "}
              <FontAwesomeIcon icon={faHandshake} />{" "}
              <FontAwesomeIcon icon={faPersonCane} />
            </>
          ),
          cta: { label: "¡Quiero ser voluntario!", action: "volunteer" },
        },
      ],
    },
  ],
  contact: {
    title: "Contáctanos y Ubícanos",
    subtitle: "¿Tienes preguntas? Escríbenos o visítanos.",
    address:
      "Herrera 360, Comuna de Santiago. (Centro Comunitario Santiago en Compañía)",
    schedule: [
      "Lunes a jueves: 09:00 – 18:00 hrs",
      "Viernes: 09:00 – 17:00 hrs",
    ],
    email: "stgojoven@munistgo.cl",
    mapEmbedUrl:
      "https://maps.google.com/maps?q=Herrera+360+Santiago&output=embed",
    photoUrl: "/ProyeccionJoven.png",
  },
};
// ──────────────────────────────────────────────────────────────────────────

// ─── Card1 (blue): ícono central, 4 textos alrededor, botón abajo ──────────
function Card1({ title, tagline, bullets = [], cta, onCta }: Card1Props) {
  return (
    <div className="paj-card1">
      {/* Título + tagline centrados arriba */}
      <div className="paj-card1__header">
        <h3 className="paj-card1__title">{title}</h3>
        {tagline && <p className="paj-card1__tagline">{tagline}</p>}
      </div>

      {/* Grilla 3 cols: texto | ícono | texto  x2 filas */}
      <div className="paj-card1__grid">
        <p className="paj-card1__bullet">{bullets[0]}</p>

        <div className="paj-card1__center" style={{ gridRow: "span 2" }}>
          {/* SVG red neuronal */}
          <svg
            viewBox="0 0 140 140"
            className="paj-neural-svg"
            aria-hidden="true"
          >
            {[
              [70, 12],
              [35, 45],
              [105, 45],
              [20, 85],
              [70, 85],
              [120, 85],
              [70, 128],
            ].map(([cx, cy], i) => (
              <circle
                key={i}
                cx={cx}
                cy={cy}
                r="9"
                className="paj-neural-node"
              />
            ))}
            {[
              [70, 12, 35, 45],
              [70, 12, 105, 45],
              [35, 45, 20, 85],
              [35, 45, 70, 85],
              [105, 45, 70, 85],
              [105, 45, 120, 85],
              [20, 85, 70, 128],
              [70, 85, 70, 128],
              [120, 85, 70, 128],
            ].map(([x1, y1, x2, y2], i) => (
              <line
                key={i}
                x1={x1}
                y1={y1}
                x2={x2}
                y2={y2}
                className="paj-neural-edge"
              />
            ))}
          </svg>
        </div>

        <p className="paj-card1__bullet">{bullets[1]}</p>
        <p className="paj-card1__bullet">{bullets[2]}</p>
        <p className="paj-card1__bullet">{bullets[3]}</p>
      </div>

      {cta && (
        <div className="paj-card__footer">
          <button
            className="paj-btn paj-btn--blue"
            onClick={() => onCta?.(cta.action)}
          >
            {cta.label}
          </button>
        </div>
      )}
    </div>
  );
}

// ─── Card2 (orange): ícono esquina, título centrado, texto grande, ícono decorativo, botón ─
function Card2({ icon, title, body, bodyIcon, cta, onCta }: Card2Props) {
  return (
    <div className="paj-card2">
      {/* Ícono esquina superior izquierda + título centrado */}
      <div className="paj-card2__top">
        <span className="paj-card2__corner-icon">{icon}</span>
        <h3 className="paj-card2__title">{title}</h3>
      </div>

      {/* Texto central */}
      <p className="paj-card2__body">{body}</p>

      {/* Ícono decorativo grande */}
      {bodyIcon && <div className="paj-card2__body-icon">{bodyIcon}</div>}

      {cta && (
        <div className="paj-card__footer">
          <button
            className="paj-btn paj-btn--orange"
            onClick={() => onCta?.(cta.action)}
          >
            {cta.label}
          </button>
        </div>
      )}
    </div>
  );
}

// ─── Selector: elige Card1 o Card2 según el theme ─────────────────────────
function Card({ theme, onCta, ...props }: CardProps) {
  if (theme === "blue")
    return <Card1 {...(props as Card1Data)} onCta={onCta} />;
  if (theme === "orange")
    return <Card2 {...(props as Card2Data)} onCta={onCta} />;
  return null;
}

// ─── Sección con carrusel ─────────────────────────────────────────────────
function CardSection({
  title,
  subtitle,
  theme,
  cards,
  onCta,
}: CardSectionProps) {
  const [index, setIndex] = useState(0);
  const card = cards[index];

  return (
    <section className={`paj-section paj-section--${theme}`}>
      <div className="paj-section__inner">
        <h2 className="paj-section__title">{title}</h2>
        <p
          className={`paj-section__subtitle ${theme === "blue" ? "paj-section__subtitle--link" : ""}`}
        >
          {subtitle}
        </p>

        {card && <Card {...card} theme={theme} onCta={onCta} />}

        <div className="paj-dots">
          {cards.map((_, i) => (
            <button
              key={i}
              className={`paj-dot ${i === index ? `paj-dot--active paj-dot--${theme}` : ""}`}
              onClick={() => setIndex(i)}
            />
          ))}
        </div>
      </div>
    </section>
  );
}

// ─── Contacto ─────────────────────────────────────────────────────────────
function ContactSection({ data }: ContactSectionProps) {
  const [form, setForm] = useState<FormState>({
    nombre: "",
    correo: "",
    mensaje: "",
  });
  const fields: { label: string; name: keyof FormState; type: string }[] = [
    { label: "Nombre", name: "nombre", type: "text" },
    { label: "Correo Electrónico", name: "correo", type: "email" },
  ];

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    const name = e.target.name as keyof FormState;
    const value = e.target.value;
    setForm((p) => ({ ...p, [name]: value }));
  };

  const handleSubmit = () => {
    alert("Mensaje enviado (mock)");
    setForm({ nombre: "", correo: "", mensaje: "" });
  };

  return (
    <section className="paj-section paj-contacto">
      <div className="paj-contacto__pin">
        <FontAwesomeIcon icon={faLocationDot} />
      </div>
      <h2 className="paj-section__title">{data.title}</h2>
      <p className="paj-section__subtitle">{data.subtitle}</p>

      <div className="paj-contacto__grid">
        <div className="paj-contacto__form">
          <h3 className="paj-contacto__col-title">Envíanos un mensaje</h3>
          {fields.map(({ label, name, type }) => (
            <div key={name}>
              <label className="paj-label">{label}</label>
              <input
                className="paj-input"
                name={name}
                type={type}
                value={form[name]}
                onChange={handleChange}
              />
            </div>
          ))}
          <label className="paj-label">Mensaje</label>
          <textarea
            className="paj-input paj-textarea"
            name="mensaje"
            value={form.mensaje}
            onChange={handleChange}
          />
          <button
            className="paj-btn paj-btn--blue paj-btn--sm"
            onClick={handleSubmit}
          >
            Enviar Mensaje
          </button>
        </div>

        <div className="paj-contacto__info">
          <h3 className="paj-contacto__col-title">Nuestra Ubicación</h3>
          {[
            {
              icon: (
                <FontAwesomeIcon icon={byPrefixAndName.fas["location-dot"]} />
              ),
              content: data.address,
            },
            {
              icon: <FontAwesomeIcon icon={byPrefixAndName.fas["clock"]} />,
              content: data.schedule.join("\n"),
            },
            {
              icon: <FontAwesomeIcon icon={byPrefixAndName.fas["envelope"]} />,
              content: data.email,
              isEmail: true,
            },
          ].map(({ icon, content, isEmail }, i) => (
            <div key={i} className="paj-contacto__info-item">
              <span className="paj-contacto__info-icon">{icon}</span>
              {isEmail ? (
                <a className="paj-contacto__email" href={`mailto:${content}`}>
                  {content}
                </a>
              ) : (
                <p style={{ whiteSpace: "pre-line" }}>{content}</p>
              )}
            </div>
          ))}
        </div>
      </div>

      <div className="paj-contacto__map-row">
        <iframe
          title="Mapa"
          src={data.mapEmbedUrl}
          allowFullScreen
          loading="lazy"
        />
        <div className="paj-contacto__photo">
          {data.photoUrl ? (
            <img src={data.photoUrl} alt="Sede" />
          ) : (
            <span className="paj-contacto__photo-placeholder">
              Foto del lugar
            </span>
          )}
        </div>
      </div>
    </section>
  );
}

// ─── Página principal ─────────────────────────────────────────────────────
export default function ProyeccionAccionJoven({
  hero = MOCK.hero,
  sections = MOCK.sections,
  contact = MOCK.contact,
  onCta = (action: string) => console.log("CTA:", action),
}: ProyeccionAccionJovenProps) {
  const [activeTab, setActiveTab] = useState(hero.tabs[0]?.id ?? "");

  return (
    <div className="paj-page">
      <section className="paj-hero">
        <div className="paj-hero__content">
          <h1 className="paj-hero__title">{hero.title}</h1>
          <p className="paj-hero__subtitle">{hero.subtitle}</p>
          <div className="paj-hero__tabs">
            {hero.tabs.map((tab) => (
              <button
                key={tab.id}
                className={`paj-tab ${activeTab === tab.id ? "paj-tab--active" : ""}`}
                onClick={() => setActiveTab(tab.id)}
              >
                <span className="paj-tab__icon">{tab.icon}</span>
                <span className="paj-tab__label">{tab.label}</span>
              </button>
            ))}
          </div>
        </div>
      </section>

      {sections.map((sec) => (
        <CardSection key={sec.id} {...sec} onCta={onCta} />
      ))}

      <ContactSection data={contact} />
    </div>
  );
}
