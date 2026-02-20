import { useState, useEffect } from 'react';
import { doctorService, departmentService } from '../services/apiService';
import { Stethoscope, Search, Edit2, Trash2 } from 'lucide-react';
import Modal from '../components/Modal';

const Doctors = () => {
    const [doctors, setDoctors] = useState([]);
    const [departments, setDepartments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchQuery, setSearchQuery] = useState('');

    // Modal states
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);

    const initialDoctorState = {
        doctorName: '',
        specialization: '',
        availableDays: [],
        department: { departmentId: '' }
    };

    const [currentDoctor, setCurrentDoctor] = useState(initialDoctorState);
    const [isEditing, setIsEditing] = useState(false);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            setLoading(true);
            const [docRes, deptRes] = await Promise.all([
                doctorService.getAllDoctors(),
                departmentService.getAllDepartments()
            ]);

            if (docRes && docRes.data) setDoctors(docRes.data);
            if (deptRes && deptRes.data) setDepartments(deptRes.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleOpenModal = (doctor = null) => {
        if (doctor) {
            setIsEditing(true);
            setCurrentDoctor({
                ...doctor,
                department: doctor.department || { departmentId: '' }
            });
        } else {
            setIsEditing(false);
            setCurrentDoctor(initialDoctorState);
        }
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setCurrentDoctor(initialDoctorState);
    };

    const handleSave = async (e) => {
        e.preventDefault();
        try {
            // Format payload to ensure department sends correctly
            const payload = {
                ...currentDoctor,
                department: { departmentId: parseInt(currentDoctor.department.departmentId) }
            };

            if (isEditing) {
                await doctorService.updateDoctor(payload);
            } else {
                await doctorService.addDoctor(payload);
            }
            handleCloseModal();
            fetchData();
        } catch (error) {
            console.error('Error saving doctor:', error);
            alert('Failed to save doctor details.');
        }
    };

    const confirmDelete = (doctor) => {
        setCurrentDoctor(doctor);
        setIsDeleteModalOpen(true);
    };

    const handleDelete = async () => {
        try {
            await doctorService.deleteDoctor(currentDoctor.doctorId);
            setIsDeleteModalOpen(false);
            fetchData();
        } catch (error) {
            console.error('Error deleting doctor:', error);
            alert('Failed to delete. Doctor might have existing appointments.');
        }
    };

    const handleDaysChange = (e) => {
        // Convert comma separated string to array for simplicity
        const daysArray = e.target.value.split(',').map(d => d.trim()).filter(d => d);
        setCurrentDoctor({ ...currentDoctor, availableDays: daysArray });
    };

    const filteredDoctors = doctors.filter(d =>
        d.doctorName?.toLowerCase().includes(searchQuery.toLowerCase()) ||
        d.specialization?.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div className="p-8">
            <div className="page-header">
                <div>
                    <h1 className="page-title">Medical Staff</h1>
                    <p className="page-subtitle">Manage hospital doctors and their specialties.</p>
                </div>
                <button className="btn-primary" onClick={() => handleOpenModal()}>
                    <Stethoscope size={18} />
                    Add Doctor
                </button>
            </div>

            <div className="glass-card p-6 mb-8">
                <div className="input-group" style={{ marginBottom: 0, flexDirection: 'row', alignItems: 'center' }}>
                    <Search size={20} className="text-secondary" style={{ position: 'absolute', marginLeft: '1rem', color: '#94a3b8' }} />
                    <input
                        type="text"
                        placeholder="Search doctors by name or specialty..."
                        className="input-field w-full"
                        style={{ paddingLeft: '3rem' }}
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                    />
                </div>
            </div>

            <div className="dashboard-grid">
                {loading ? (
                    <div className="glass-card p-6"><p>Loading medical staff...</p></div>
                ) : filteredDoctors.length === 0 ? (
                    <div className="glass-card p-6" style={{ gridColumn: '1 / -1', textAlign: 'center' }}>
                        <p className="text-secondary">No doctors found in the directory.</p>
                    </div>
                ) : (
                    filteredDoctors.map((doctor) => (
                        <div key={doctor.doctorId} className="glass-card p-6" style={{ position: 'relative', display: 'flex', flexDirection: 'column', alignItems: 'center', textAlign: 'center' }}>

                            <div style={{ position: 'absolute', top: '1rem', right: '1rem', display: 'flex', gap: '0.5rem' }}>
                                <button onClick={() => handleOpenModal(doctor)} style={{ background: 'transparent', border: 'none', color: '#cbd5e1', cursor: 'pointer' }} className="hover:text-white transition-colors">
                                    <Edit2 size={16} />
                                </button>
                                <button onClick={() => confirmDelete(doctor)} style={{ background: 'transparent', border: 'none', color: '#ef4444', cursor: 'pointer' }} className="hover:text-red-400 transition-colors">
                                    <Trash2 size={16} />
                                </button>
                            </div>

                            <div
                                style={{
                                    width: '80px', height: '80px', borderRadius: '50%',
                                    background: 'linear-gradient(135deg, var(--primary-color), var(--accent-color))',
                                    display: 'flex', alignItems: 'center', justifyContent: 'center',
                                    marginBottom: '1rem', fontSize: '2rem', fontWeight: 'bold'
                                }}>
                                {doctor.doctorName ? doctor.doctorName.charAt(0).toUpperCase() : 'D'}
                            </div>
                            <h3 style={{ fontSize: '1.25rem', fontWeight: 600, color: 'white' }}>Dr. {doctor.doctorName}</h3>
                            <p style={{ color: 'var(--accent-color)', fontWeight: 500, marginBottom: '0.5rem' }}>{doctor.specialization}</p>

                            <div style={{ padding: '1rem', background: 'rgba(255,255,255,0.05)', borderRadius: '8px', width: '100%', marginBottom: '1rem' }}>
                                <p style={{ color: 'var(--text-secondary)', fontSize: '0.875rem' }}>
                                    Dept: <span style={{ color: 'white' }}>{doctor.department ? doctor.department.departmentName : 'Unassigned'}</span>
                                </p>
                                <p style={{ color: 'var(--text-secondary)', fontSize: '0.875rem', marginTop: '4px' }}>
                                    Days: <span style={{ color: 'white' }}>{doctor.availableDays && doctor.availableDays.length > 0 ? doctor.availableDays.join(', ') : 'Not scheduled'}</span>
                                </p>
                            </div>
                        </div>
                    ))
                )}
            </div>

            {/* Add/Edit Modal */}
            <Modal isOpen={isModalOpen} onClose={handleCloseModal} title={isEditing ? 'Edit Doctor' : 'Onboard Doctor'}>
                <form onSubmit={handleSave} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                    <div className="input-group">
                        <label>Doctor Name</label>
                        <input
                            type="text"
                            className="input-field"
                            placeholder="e.g. Jane Doe"
                            required
                            value={currentDoctor.doctorName}
                            onChange={(e) => setCurrentDoctor({ ...currentDoctor, doctorName: e.target.value })}
                        />
                    </div>

                    <div className="input-group">
                        <label>Specialization</label>
                        <input
                            type="text"
                            className="input-field"
                            placeholder="e.g. Cardiologist"
                            required
                            value={currentDoctor.specialization}
                            onChange={(e) => setCurrentDoctor({ ...currentDoctor, specialization: e.target.value })}
                        />
                    </div>

                    <div className="input-group">
                        <label>Department</label>
                        <select
                            className="input-field"
                            required
                            value={currentDoctor.department.departmentId}
                            onChange={(e) => setCurrentDoctor({ ...currentDoctor, department: { departmentId: e.target.value } })}
                        >
                            <option value="" disabled>Select Department</option>
                            {departments.map(dept => (
                                <option key={dept.departmentId} value={dept.departmentId}>{dept.departmentName}</option>
                            ))}
                        </select>
                    </div>

                    <div className="input-group">
                        <label>Available Days (comma separated)</label>
                        <input
                            type="text"
                            className="input-field"
                            placeholder="e.g. Monday, Wednesday, Friday"
                            value={currentDoctor.availableDays.join(', ')}
                            onChange={handleDaysChange}
                        />
                    </div>

                    <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '1rem', marginTop: '1rem' }}>
                        <button type="button" className="btn-secondary" onClick={handleCloseModal}>Cancel</button>
                        <button type="submit" className="btn-primary" style={{ padding: '0.75rem 1.5rem' }}>Save Doctor</button>
                    </div>
                </form>
            </Modal>

            {/* Delete Confirmation Modal */}
            <Modal isOpen={isDeleteModalOpen} onClose={() => setIsDeleteModalOpen(false)} title="Confirm Removal">
                <p style={{ color: 'var(--text-secondary)', marginBottom: '1.5rem' }}>
                    Are you sure you want to remove <strong>Dr. {currentDoctor?.doctorName}</strong> from the system?
                </p>
                <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '1rem' }}>
                    <button className="btn-secondary" onClick={() => setIsDeleteModalOpen(false)}>Cancel</button>
                    <button className="btn-primary" style={{ background: '#ef4444', borderColor: '#ef4444' }} onClick={handleDelete}>Remove</button>
                </div>
            </Modal>

        </div>
    );
};

export default Doctors;
