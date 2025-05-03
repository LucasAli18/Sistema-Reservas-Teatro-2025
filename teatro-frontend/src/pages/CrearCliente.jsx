import { useEffect, useState } from 'react';
import clienteService from '../services/clienteService';
import { useNavigate } from 'react-router-dom';

export default function CrearCliente() {
  const [clientes, setClientes] = useState([]);
  const [nuevo, setNuevo] = useState({ dni: '', nombre: '', email: '', tieneEntradaGratis: false });
  const [modoEdicion, setModoEdicion] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    clienteService.getClientes().then(setClientes);
  }, []);

  const cargarClientes = () => clienteService.getClientes().then(setClientes);

  const crear = () => {
    clienteService.crearCliente(nuevo).then(() => {
      cargarClientes();
      setNuevo({ dni: '', nombre: '', email: '', tieneEntradaGratis: false });
    });
  };

  const editar = () => {
    clienteService.editarCliente(nuevo.dni, {
      nombre: nuevo.nombre,
      email: nuevo.email,
      tieneEntradaGratis: nuevo.tieneEntradaGratis
    }).then(() => {
      cargarClientes();
      setNuevo({ dni: '', nombre: '', email: '', tieneEntradaGratis: false });
      setModoEdicion(false);
    });
  };

  const eliminar = (dni) => {
    clienteService.eliminarCliente(dni).then(cargarClientes);
  };

  const cargarParaEditar = (cliente) => {
    setNuevo({
      dni: cliente.dni,
      nombre: cliente.nombre,
      email: cliente.email,
      tieneEntradaGratis: cliente.tieneEntradaGratis || false
    });
    setModoEdicion(true);
  };

  return (
    <div>
      <h2>Clientes</h2>
      <form onSubmit={(e) => { e.preventDefault(); modoEdicion ? editar() : crear(); }}>
        <input required placeholder="DNI" value={nuevo.dni} onChange={e => setNuevo({ ...nuevo, dni: e.target.value })} disabled={modoEdicion} />
        <input required placeholder="Nombre" value={nuevo.nombre} onChange={e => setNuevo({ ...nuevo, nombre: e.target.value })} />
        <input required type="email" placeholder="Email" value={nuevo.email} onChange={e => setNuevo({ ...nuevo, email: e.target.value })} />
        <button type="submit">{modoEdicion ? 'Actualizar' : 'Crear'}</button>
      </form>

      <ul>
        {clientes.map(c => (
          <li key={c.dni}>
            {c.nombre} - {c.email} - ({c.eventosParticipados} reservas)
            <button onClick={() => navigate(`/clientes/${c.dni}/reservas`)}>Ver Reservas</button>
            <button onClick={() => cargarParaEditar(c)}>Editar</button>
            <button onClick={() => eliminar(c.dni)}>Eliminar</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
