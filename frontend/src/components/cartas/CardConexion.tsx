export interface CardData {
  img: string;
  icon: string;
  iconColor: string;
  title: string;
  description: string;
  date: string;
  lugar: string;
  cupos_disponibles: number;
  cupos: number;
  btn: string;
  btnClass: "participar" | "cerrado" | "cancelado" | "lleno";
}

export default function Card({
  data,
  onClick,
}: {
  data: CardData;
  onClick: () => void;
}) {
  return (
    <div className="card" onClick={onClick}>
      <img src={data.img} className="card_img" alt={data.title} />
      <div className="card_body">
        <div className="card_header">
          <span className="card_icon">
            <span
              style={{ color: data.iconColor }}
              className="card-header-icon material-symbols-outlined"
            >
              {data.icon}
            </span>
          </span>
          <span className="card_title">{data.title}</span>
        </div>
        <p className="card_description">{data.description}</p>
        <div className="card_footer">
          <span className="card_date">{data.date}</span>
          <button
            className={`card_btn ${data.btnClass}`}
            onClick={(e) => {
              e.stopPropagation();
              onClick();
            }}
          >
            {data.btn}
          </button>
        </div>
      </div>
    </div>
  );
}
