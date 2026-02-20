import { useState, useEffect } from 'react';
import { patientService } from '../services/apiService';
import { UserPlus, Search, Edit2 } from 'lucide-react';
import Modal from '../components/Modal';

const Patients = () => {
    const [patients, setPatients] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchQuery, setSearchQuery] = useState('');

    // Modal states
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [currentPatient, setCurrentPatient] = useState({ patientName: '', age: '', gender: '', phone: '', email: '' });
    const [isEditing, setIsEditing] = useState(false);

    useEffect(() => {
        fetchPatients();
    }, []);

    const fetchPatients = async () => {
        try {
            setLoading(true);
            const response = await patientService.getAllPatients();
            if (response && response.data) {
                setPatients(response.data);
            }
        } catch (error) {
            console.error('Error fetching patients:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleOpenModal = (patient = null) => {
        if (patient) {
            setIsEditing(true);
            setCurrentPatient(patient);
        } else {
            setIsEditing(false);
            setCurrentPatient({ patientName: '', age: '', gender: '', phone: '', email: '' });
        }
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setCurrentPatient({ patientName: '', age: '', gender: '', phone: '', email: '' });
    };

    const handleSave = async (e) => {
        e.preventDefault();
        try {
            if (isEditing) {
                await patientService.updatePatient(currentPatient);
            } else {
                await patientService.registerPatient(currentPatient);
            }
            handleCloseModal();
            fetchPatients();
        } catch (error) {
            console.error('Error saving patient:', error);
            alert('Failed to save patient. Please check if email and phone are unique.');
        }
    };

    const filteredPatients = patients.filter(p =>
        p.patientName?.toLowerCase().includes(searchQuery.toLowerCase()) ||
        p.email?.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div className="p-8">
            <div className="page-header">
                <div>
                    <h1 className="page-title">Patient Records</h1>
                    <p className="page-subtitle">Manage and view all registered patients.</p>
                </div>
                <button className="btn-primary" onClick={() => handleOpenModal()}>
                    <UserPlus size={18} />
                    Register Patient
                </button>
            </div>

            <div className="glass-card p-6 mb-8">
                <div className="input-group" style={{ marginBottom: 0, flexDirection: 'row', alignItems: 'center' }}>
                    <Search size={20} className="text-secondary" style={{ position: 'absolute', marginLeft: '1rem', color: '#94a3b8' }} />
                    <input
                        type="text"
                        placeholder="Search patients by name or email..."
                        className="input-field w-full"
                        style={{ paddingLeft: '3rem' }}
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                    />
                </div>
            </div>

            <div className="glass-panel" style={{ overflow: 'hidden' }}>
                <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
                    <thead>
                        <tr style={{ borderBottom: '1px solid var(--surface-border)', background: 'rgba(255,255,255,0.02)' }}>
                            <th style={{ padding: '1rem 1.5rem', fontWeight: 600, color: 'var(--text-secondary)' }}>ID</th>
                            <th style={{ padding: '1rem 1.5rem', fontWeight: 600, color: 'var(--text-secondary)' }}>Patient Name</th>
                            <th style={{ padding: '1rem 1.5rem', fontWeight: 600, color: 'var(--text-secondary)' }}>Age / Gender</th>
                            <th style={{ padding: '1rem 1.5rem', fontWeight: 600, color: 'var(--text-secondary)' }}>Contact Info</th>
                            <th style={{ padding: '1rem 1.5rem', fontWeight: 600, color: 'var(--text-secondary)' }}>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {loading ? (
                            <tr><td colSpan="5" style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-secondary)' }}>Loading patients...</td></tr>
                        ) : filteredPatients.length === 0 ? (
                            <tr><td colSpan="5" style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-secondary)' }}>No patients found.</td></tr>
                        ) : (
                            filteredPatients.map((patient) => (
                                <tr key={patient.patientId} style={{ borderBottom: '1px solid var(--surface-border)' }} className="hover:bg-[rgba(255,255,255,0.02)] transition-colors">
                                    <td style={{ padding: '1rem 1.5rem' }}>#{patient.patientId}</td>
                                    <td style={{ padding: '1rem 1.5rem', fontWeight: 500, color: 'white' }}>{patient.patientName}</td>
                                    <td style={{ padding: '1rem 1.5rem' }}>
                                        <span style={{
                                            background: 'rgba(239, 68, 68, 0.2)',
                                            color: '#ef4444',
                                            padding: '0.25rem 0.75rem',
                                            borderRadius: '999px',
                                            fontSize: '0.875rem',
                                            fontWeight: 500,
                                            marginRight: '0.5rem'
                                        }}>
                                            {patient.age}y
                                        </span>
                                        <span style={{ color: 'var(--text-secondary)', fontSize: '0.875rem' }}>{patient.gender}</span>
                                    </td>
                                    <td style={{ padding: '1rem 1.5rem' }}>
                                        <div style={{ display: 'flex', flexDirection: 'column' }}>
                                            <span style={{ color: 'white', fontSize: '0.875rem' }}>{patient.email}</span>
                                            <span style={{ color: 'var(--text-secondary)', fontSize: '0.875rem' }}>{patient.phone}</span>
                                        </div>
                                    </td>
                                    <td style={{ padding: '1rem 1.5rem' }}>
                                        <button onClick={() => handleOpenModal(patient)} style={{ background: 'transparent', border: 'none', color: '#cbd5e1', cursor: 'pointer', padding: '0.5rem' }} className="hover:text-white transition-colors">
                                            <Edit2 size={18} />
                                        </button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            {/* Add/Edit Modal */}
            <Modal isOpen={isModalOpen} onClose={handleCloseModal} title={isEditing ? 'Edit Patient' : 'Register New Patient'}>
                <form onSubmit={handleSave} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                    <div className="input-group">
                        <label>Full Name</label>
                        <input
                            type="text"
                            className="input-field"
                            placeholder="John Doe"
                            required
                            value={currentPatient.patientName}
                            onChange={(e) => setCurrentPatient({ ...currentPatient, patientName: e.target.value })}
                        />
                    </div>
                    <div style={{ display: 'flex', gap: '1rem' }}>
                        <div className="input-group" style={{ flex: 1 }}>
                            <label>Age</label>
                            <input
                                type="number"
                                className="input-field"
                                placeholder="30"
                                required
                                value={currentPatient.age}
                                onChange={(e) => setCurrentPatient({ ...currentPatient, age: e.target.value })}
                            />
                        </div>
                        <div className="input-group" style={{ flex: 1 }}>
                            <label>Gender</label>
                            <select
                                className="input-field"
                                required
                                value={currentPatient.gender}
                                onChange={(e) => setCurrentPatient({ ...currentPatient, gender: e.target.value })}
                            >
                                <option value="" disabled>Select</option>
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                                <option value="Other">Other</option>
                            </select>
                        </div>
                    </div>
                    <div className="input-group">
                        <label>Phone Number</label>
                        <input
                            type="number"
                            className="input-field"
                            placeholder="1234567890"
                            required
                            value={currentPatient.phone}
                            onChange={(e) => setCurrentPatient({ ...currentPatient, phone: e.target.value })}
                        />
                    </div>
                    <div className="input-group">
                        <label>Email Address</label>
                        <input
                            type="email"
                            className="input-field"
                            placeholder="john@example.com"
                            required
                            value={currentPatient.email}
                            onChange={(e) => setCurrentPatient({ ...currentPatient, email: e.target.value })}
                        />
                    </div>

                    <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '1rem', marginTop: '1rem' }}>
                        <button type="button" className="btn-secondary" onClick={handleCloseModal}>Cancel</button>
                        <button type="submit" className="btn-primary" style={{ padding: '0.75rem 1.5rem' }}>Save Patient</button>
                    </div>
                </form>
            </Modal>
        </div>
    );
};

export default Patients;
