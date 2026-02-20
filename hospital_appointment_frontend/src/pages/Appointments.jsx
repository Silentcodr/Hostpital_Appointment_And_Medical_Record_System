import { useState, useEffect } from 'react';
import { appointmentService, doctorService, patientService } from '../services/apiService';
import { Calendar, Search, Edit2, Trash2, Clock } from 'lucide-react';
import Modal from '../components/Modal';

const Appointments = () => {
    const [appointments, setAppointments] = useState([]);
    const [doctors, setDoctors] = useState([]);
    const [patients, setPatients] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchQuery, setSearchQuery] = useState('');

    // Modal states
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);

    const initialAppointmentState = {
        appointmentDateTime: '',
        status: 'BOOKED',
        patient: { patientId: '' },
        doctor: { doctorId: '' }
    };

    const [currentAppointment, setCurrentAppointment] = useState(initialAppointmentState);
    const [isEditing, setIsEditing] = useState(false);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            setLoading(true);
            const [apptRes, docRes, patRes] = await Promise.all([
                appointmentService.getAllAppointments(),
                doctorService.getAllDoctors(),
                patientService.getAllPatients()
            ]);

            if (apptRes && apptRes.data) setAppointments(apptRes.data);
            if (docRes && docRes.data) setDoctors(docRes.data);
            if (patRes && patRes.data) setPatients(patRes.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleOpenModal = (appointment = null) => {
        if (appointment) {
            setIsEditing(true);
            setCurrentAppointment({
                ...appointment,
                patient: appointment.patient || { patientId: '' },
                doctor: appointment.doctor || { doctorId: '' },
                // Format ISO date for datetime-local input
                appointmentDateTime: appointment.appointmentDateTime ? new Date(appointment.appointmentDateTime).toISOString().slice(0, 16) : ''
            });
        } else {
            setIsEditing(false);
            setCurrentAppointment(initialAppointmentState);
        }
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setCurrentAppointment(initialAppointmentState);
    };

    const handleSave = async (e) => {
        e.preventDefault();
        try {
            const payload = {
                ...currentAppointment,
                patient: { patientId: parseInt(currentAppointment.patient.patientId) },
                doctor: { doctorId: parseInt(currentAppointment.doctor.doctorId) },
                // Format strict date for Spring Boot LocalDateTime deserialization if needed, or Spring boot handles ISO natively.
                appointmentDateTime: currentAppointment.appointmentDateTime
            };

            if (isEditing) {
                await appointmentService.updateAppointment(payload);
            } else {
                await appointmentService.bookAppointment(payload);
            }
            handleCloseModal();
            fetchData();
        } catch (error) {
            console.error('Error saving appointment:', error);
            alert('Failed to save appointment. Please check all fields.');
        }
    };

    const confirmDelete = (appointment) => {
        setCurrentAppointment(appointment);
        setIsDeleteModalOpen(true);
    };

    const handleDelete = async () => {
        try {
            await appointmentService.cancelAppointment(currentAppointment.appointmentId);
            setIsDeleteModalOpen(false);
            fetchData();
        } catch (error) {
            console.error('Error deleting appointment:', error);
            alert('Failed to drop appointment.');
        }
    };

    const filteredAppointments = appointments.filter(a =>
        a.patient?.patientName?.toLowerCase().includes(searchQuery.toLowerCase()) ||
        a.doctor?.doctorName?.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div className="p-8">
            <div className="page-header">
                <div>
                    <h1 className="page-title">Appointments</h1>
                    <p className="page-subtitle">Manage upcoming schedules and patient visits.</p>
                </div>
                <button className="btn-primary" onClick={() => handleOpenModal()}>
                    <Calendar size={18} />
                    Book Appointment
                </button>
            </div>

            <div className="glass-card p-6 mb-8">
                <div className="input-group" style={{ marginBottom: 0, flexDirection: 'row', alignItems: 'center' }}>
                    <Search size={20} className="text-secondary" style={{ position: 'absolute', marginLeft: '1rem', color: '#94a3b8' }} />
                    <input
                        type="text"
                        placeholder="Search by patient or doctor..."
                        className="input-field w-full"
                        style={{ paddingLeft: '3rem' }}
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                    />
                </div>
            </div>

            <div className="dashboard-grid">
                {loading ? (
                    <div className="glass-card p-6"><p>Loading schedule...</p></div>
                ) : filteredAppointments.length === 0 ? (
                    <div className="glass-card p-6" style={{ gridColumn: '1 / -1', textAlign: 'center' }}>
                        <p className="text-secondary">No appointments scheduled.</p>
                    </div>
                ) : (
                    filteredAppointments.map((appt) => {
                        const date = new Date(appt.appointmentDateTime);
                        const formattedDate = date.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
                        const formattedTime = date.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' });

                        return (
                            <div key={appt.appointmentId} className="glass-card p-6" style={{ display: 'flex', flexDirection: 'column' }}>
                                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '1.5rem' }}>
                                    <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
                                        <div style={{
                                            width: '48px', height: '48px', borderRadius: '12px',
                                            background: 'rgba(14, 165, 233, 0.2)', color: '#0ea5e9',
                                            display: 'flex', alignItems: 'center', justifyContent: 'center'
                                        }}>
                                            <Clock size={24} />
                                        </div>
                                        <div>
                                            <h3 style={{ fontSize: '1.1rem', fontWeight: 600, color: 'white' }}>{formattedDate}</h3>
                                            <p style={{ color: 'var(--accent-color)', fontWeight: 500 }}>{formattedTime}</p>
                                        </div>
                                    </div>
                                    <div style={{ display: 'flex', gap: '0.5rem' }}>
                                        <button onClick={() => handleOpenModal(appt)} style={{ background: 'transparent', border: 'none', color: '#cbd5e1', cursor: 'pointer' }} className="hover:text-white transition-colors">
                                            <Edit2 size={16} />
                                        </button>
                                        <button onClick={() => confirmDelete(appt)} style={{ background: 'transparent', border: 'none', color: '#ef4444', cursor: 'pointer' }} className="hover:text-red-400 transition-colors">
                                            <Trash2 size={16} />
                                        </button>
                                    </div>
                                </div>

                                <div style={{ borderTop: '1px solid var(--surface-border)', paddingTop: '1rem' }}>
                                    <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.5rem' }}>
                                        <span style={{ color: 'var(--text-secondary)', fontSize: '0.875rem' }}>Patient</span>
                                        <span style={{ color: 'white', fontWeight: 500 }}>{appt.patient ? appt.patient.patientName : 'Unknown'}</span>
                                    </div>
                                    <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.5rem' }}>
                                        <span style={{ color: 'var(--text-secondary)', fontSize: '0.875rem' }}>Doctor</span>
                                        <span style={{ color: 'white', fontWeight: 500 }}>Dr. {appt.doctor ? appt.doctor.doctorName : 'Unknown'}</span>
                                    </div>
                                    <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '1rem' }}>
                                        <span style={{
                                            padding: '0.25rem 0.75rem', borderRadius: '999px', fontSize: '0.75rem', fontWeight: 600,
                                            backgroundColor: appt.status === 'BOOKED' ? 'rgba(59, 130, 246, 0.2)' :
                                                appt.status === 'COMPLETED' ? 'rgba(34, 197, 94, 0.2)' : 'rgba(239, 68, 68, 0.2)',
                                            color: appt.status === 'BOOKED' ? '#3b82f6' :
                                                appt.status === 'COMPLETED' ? '#22c5e1' : '#ef4444'
                                        }}>
                                            {appt.status}
                                        </span>
                                        <span style={{ color: 'var(--text-secondary)', fontSize: '0.75rem' }}>ID: #{appt.appointmentId}</span>
                                    </div>
                                </div>
                            </div>
                        )
                    })
                )}
            </div>

            {/* Add/Edit Modal */}
            <Modal isOpen={isModalOpen} onClose={handleCloseModal} title={isEditing ? 'Reschedule / Update' : 'New Appointment'}>
                <form onSubmit={handleSave} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>

                    <div className="input-group">
                        <label>Patient</label>
                        <select
                            className="input-field"
                            required
                            value={currentAppointment.patient.patientId}
                            onChange={(e) => setCurrentAppointment({ ...currentAppointment, patient: { patientId: e.target.value } })}
                        >
                            <option value="" disabled>Select Patient</option>
                            {patients.map(p => (
                                <option key={p.patientId} value={p.patientId}>{p.patientName} (ID: {p.patientId})</option>
                            ))}
                        </select>
                    </div>

                    <div className="input-group">
                        <label>Doctor</label>
                        <select
                            className="input-field"
                            required
                            value={currentAppointment.doctor.doctorId}
                            onChange={(e) => setCurrentAppointment({ ...currentAppointment, doctor: { doctorId: e.target.value } })}
                        >
                            <option value="" disabled>Select Doctor</option>
                            {doctors.map(d => (
                                <option key={d.doctorId} value={d.doctorId}>Dr. {d.doctorName} - {d.specialization}</option>
                            ))}
                        </select>
                    </div>

                    <div className="input-group">
                        <label>Date & Time</label>
                        <input
                            type="datetime-local"
                            className="input-field"
                            required
                            value={currentAppointment.appointmentDateTime}
                            onChange={(e) => setCurrentAppointment({ ...currentAppointment, appointmentDateTime: e.target.value })}
                        />
                    </div>

                    <div className="input-group">
                        <label>Status</label>
                        <select
                            className="input-field"
                            required
                            value={currentAppointment.status}
                            onChange={(e) => setCurrentAppointment({ ...currentAppointment, status: e.target.value })}
                        >
                            <option value="BOOKED">BOOKED</option>
                            <option value="COMPLETED">COMPLETED</option>
                            <option value="CANCELLED">CANCELLED</option>
                        </select>
                    </div>

                    <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '1rem', marginTop: '1rem' }}>
                        <button type="button" className="btn-secondary" onClick={handleCloseModal}>Cancel</button>
                        <button type="submit" className="btn-primary" style={{ padding: '0.75rem 1.5rem' }}>Save Appointment</button>
                    </div>
                </form>
            </Modal>

            {/* Delete Confirmation Modal */}
            <Modal isOpen={isDeleteModalOpen} onClose={() => setIsDeleteModalOpen(false)} title="Cancel Appointment">
                <p style={{ color: 'var(--text-secondary)', marginBottom: '1.5rem' }}>
                    Are you sure you want to completely remove this appointment record from the system?
                </p>
                <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '1rem' }}>
                    <button className="btn-secondary" onClick={() => setIsDeleteModalOpen(false)}>Cancel</button>
                    <button className="btn-primary" style={{ background: '#ef4444', borderColor: '#ef4444' }} onClick={handleDelete}>Remove</button>
                </div>
            </Modal>

        </div>
    );
};

export default Appointments;
