import axios from 'axios';
const API = 'http://localhost:8080/clientes';

export default {
  getClientes: () => axios.get(API).then(res => res.data),
  crearCliente: (cliente) => axios.post(API, cliente),
  eliminarCliente: (dni) => axios.delete(`${API}/${dni}`),
  editarCliente: (dni, data) => axios.put(`${API}/${dni}`, data),
  getReservasPorCliente: (dni) => axios.get(`${API}/${dni}/reservas`).then(res => res.data),
  editarEstadoReserva: (id, estado) =>
    axios.put(`${API}/${id}/reservas`, JSON.stringify(estado), {
      headers: { 'Content-Type': 'application/json' }
    })
};
