/**
 * @fileoverview Contexto global para el manejo del modo oscuro.
 * Provee el estado y la función para alternar entre modo claro y oscuro.
 */

import { createContext, useContext, useState } from "react";

/**
 * Contexto de tema con valores por defecto.
 * @type {React.Context<{modoOscuro: boolean, toggleModo: () => void}>}
 */
const ThemeContext = createContext({ modoOscuro: false, toggleModo: () => {} });

/**
 * Proveedor del contexto de tema.
 * Envuelve la aplicación para dar acceso al modo oscuro desde cualquier componente.
 *
 * @component
 * @param {Object} props
 * @param {React.ReactNode} props.children - Componentes hijos que tendrán acceso al contexto.
 * @returns {JSX.Element} Proveedor del contexto con el tema aplicado.
 *
 * @example
 * <ThemeProvider>
 *   <App />
 * </ThemeProvider>
 */
export function ThemeProvider({ children }: { children: React.ReactNode }) {
  const [modoOscuro, setModoOscuro] = useState(false);

  /**
   * Alterna entre modo oscuro y modo claro.
   * @function
   */
  const toggleModo = () => setModoOscuro(!modoOscuro);

  return (
    <ThemeContext.Provider value={{ modoOscuro, toggleModo }}>
      {children}
    </ThemeContext.Provider>
  );
}

/**
 * Hook personalizado para acceder al contexto de tema.
 * Debe usarse dentro de un componente envuelto por {@link ThemeProvider}.
 *
 * @returns {{ modoOscuro: boolean, toggleModo: () => void }} Estado y función del tema.
 *
 * @example
 * const { modoOscuro, toggleModo } = useTheme();
 */
export const useTheme = () => useContext(ThemeContext);