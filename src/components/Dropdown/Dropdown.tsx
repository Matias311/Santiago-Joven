import { useState } from "react";
import { Link } from "react-router-dom";
import "./Dropdown.css";

export interface DropdownItem {
  label: string;
  to: string;
  icon?: React.ReactNode;
}

interface DropdownProps {
  label: string;
  icon?: React.ReactNode;
  items: DropdownItem[];
}

export default function Dropdown({ label, icon, items }: DropdownProps) {
  const [open, setOpen] = useState(false);

  return (
    <div
      className="dropdown"
      onMouseEnter={() => setOpen(true)}
      onMouseLeave={() => setOpen(false)}
    >
      <button className="dropdown-btn">
        {label}
        {icon && <span className="icon">{icon}</span>}
      </button>

      {open && (
        <ul className="dropdown-menu">
          {items.map((item, i) => (
            <li key={i}>
              <Link to={item.to}>
                {item.icon && <span className="item-icon">{item.icon}</span>}
                {item.label}
              </Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
